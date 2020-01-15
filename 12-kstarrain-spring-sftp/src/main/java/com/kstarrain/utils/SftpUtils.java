package com.kstarrain.utils;

import com.jcraft.jsch.*;
import com.kstarrain.config.properties.SftpProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Arrays;

/**
 * @author: DongYu
 * @create: 2019-12-23 17:47
 * @description:
 */
@Slf4j
public class SftpUtils {

    private static SftpProperties sftpConfig;

    public static void setSftpConfig(SftpProperties sftpConfig) {
        SftpUtils.sftpConfig = sftpConfig;
    }


    // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
    private static final String SESSION_CONFIG_STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";

    /**
     * 创建SFTP连接
     * @return
     * @throws Exception
     */
    private static ChannelSftp createSftp() throws JSchException {

        JSch jsch = new JSch();
        log.debug("Try to connect sftp[" + sftpConfig.getUserName() + "@" + sftpConfig.getHost() + "], use password[" + sftpConfig.getPassword() + "]");

        Session session = createSession(jsch, sftpConfig.getHost(), sftpConfig.getUserName(), sftpConfig.getPort());
        session.setPassword(sftpConfig.getPassword());
        // 设置登陆超时时间
        if (sftpConfig.getSessionConnectTimeout() == null){
            session.connect();
        } else {
            session.connect(sftpConfig.getSessionConnectTimeout());
        }
        log.debug("Session connected to {}.", sftpConfig.getHost());

        Channel channel;
        if (StringUtils.isBlank(sftpConfig.getProtocol())){
            channel = session.openChannel("sftp");
        } else {
            channel = session.openChannel(sftpConfig.getProtocol());
        }

        if (sftpConfig.getChannelConnectedTimeout() == null) {
            channel.connect();
        } else {
            channel.connect(sftpConfig.getChannelConnectedTimeout());
        }
        log.debug("Channel created to {}.", sftpConfig.getHost());

        return (ChannelSftp) channel;
    }

    /**
     * 加密秘钥方式登陆
     * @return
     */
    private ChannelSftp connectByKey() throws JSchException {

        JSch jsch = new JSch();
        // 设置密钥和密码 ,支持密钥的方式登陆
        if (StringUtils.isNotBlank(sftpConfig.getPrivateKey())) {
            if (StringUtils.isNotBlank(sftpConfig.getPassphrase())) {
                // 设置带口令的密钥
                jsch.addIdentity(sftpConfig.getPrivateKey(), sftpConfig.getPassphrase());
            } else {
                // 设置不带口令的密钥
                jsch.addIdentity(sftpConfig.getPrivateKey());
            }
        }
        log.debug("Try to connect sftp[" + sftpConfig.getUserName() + "@" + sftpConfig.getHost() + "], use private key[" + sftpConfig.getPrivateKey()
                + "] with passphrase[" + sftpConfig.getPassphrase() + "]");

        Session session = createSession(jsch, sftpConfig.getHost(), sftpConfig.getUserName(), sftpConfig.getPort());
        if (sftpConfig.getSessionConnectTimeout() != null){
            session.connect(sftpConfig.getSessionConnectTimeout());
        } else {
            session.connect();
        }
        log.debug("Session connected to " + sftpConfig.getHost() + ".");

        // 创建sftp通信通道
        Channel channel = session.openChannel(sftpConfig.getProtocol());
        if (sftpConfig.getChannelConnectedTimeout() != null) {
            channel.connect(sftpConfig.getChannelConnectedTimeout());
        } else {
            channel.connect();
        }
        log.debug("Channel created to " + sftpConfig.getHost() + ".");
        return (ChannelSftp) channel;
    }

    /**
     * 创建session
     * @param jsch
     * @param host
     * @param username
     * @param port
     * @return
     * @throws Exception
     */
    private static Session createSession(JSch jsch, String host, String username, Integer port) throws JSchException {

        Session session;
        if (port == null || port <= 0) {
            session = jsch.getSession(username, host);
        } else {
            session = jsch.getSession(username, host, port);
        }
        if (StringUtils.isBlank(sftpConfig.getSessionStrictHostKeyChecking())){
            session.setConfig(SESSION_CONFIG_STRICT_HOST_KEY_CHECKING, "no");
        } else {
            session.setConfig(SESSION_CONFIG_STRICT_HOST_KEY_CHECKING, sftpConfig.getSessionStrictHostKeyChecking());
        }

        return session;
    }

    /**
     * 关闭连接
     * @param sftp
     */
    private static void disconnect(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                } else if (sftp.isClosed()) {
                    log.info("sftp is closed already");
                }
                if (null != sftp.getSession()) {
                    sftp.getSession().disconnect();
                }
            }
        } catch (JSchException e) {
            log.error("disconnect failure", e);
        }
    }


    /**
     * 将inputStream上传到指定路径下(单级或多级目录)
     * @param sftpPath  目标sftp服务器下的路径
     * @param inputStream
     * @throws JSchException
     */
    public static void uploadFile(String sftpPath, InputStream inputStream) throws JSchException {
        ChannelSftp sftp = createSftp();
        try {
            int index = sftpPath.lastIndexOf("/");
            String fileDir = sftpPath.substring(0, index);
            String fileName = sftpPath.substring(index + 1);

            createDirs(fileDir, sftp);
            sftp.put(inputStream, fileName);
            log.info("Upload file success. TargetPath: {}", sftpPath);
        } catch (Exception e) {
            log.error("Upload file failure. TargetPath: {}", sftpPath, e);
            throw new RuntimeException("Upload File failure");
        } finally {
            disconnect(sftp);
        }
    }


    /**
     * 将文件上传到指定目录
     * @param sftpPath 目标sftp服务器下的路径
     * @param file
     * @throws JSchException
     * @throws IOException
     */
    public static void uploadFile(String sftpPath, File file) throws JSchException, IOException {
        try(InputStream inputStream = new FileInputStream(file)) {
            uploadFile(sftpPath, inputStream);
        }
    }


    /**
     * 下载文件
     * @param sftpPath
     * @param targetPath
     * @return
     * @throws JSchException
     * @throws IOException
     * @throws SftpException
     */
    public static File downloadFile(String sftpPath, String targetPath) throws IOException, JSchException {
        ChannelSftp sftp = createSftp();
        OutputStream outputStream = null;
        try {
            File file = new File(targetPath);
            FileUtils.forceMkdirParent(file);
            outputStream = new FileOutputStream(file);
            sftp.get(sftpPath, outputStream);
            log.info("Download file success. TargetPath: {}", targetPath);
            return file;
        } catch (Exception e) {
            log.error("Download file failure. TargetPath: {}", sftpPath, e);
            throw new RuntimeException("Download File failure");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            disconnect(sftp);
        }
    }



    /**
     * 创建多级目录
     * @param dirPath
     * @param sftp
     * @return
     */
    private static void createDirs(String dirPath, ChannelSftp sftp) {
        if (StringUtils.isNotBlank(dirPath) && sftp != null) {

            String[] dirs = Arrays.stream(dirPath.split("/")).filter(StringUtils::isNotBlank).toArray(String[]::new);

            for (String dir : dirs) {
                try {
                    sftp.cd(dir);
                    log.debug("Change directory {}", dir);
                } catch (Exception e) {
                    try {
                        sftp.mkdir(dir);
                        log.debug("Create directory {}", dir);
                    } catch (SftpException e1) {
                        log.error("Create directory failure, directory:{}", dir, e1);
                        throw new RuntimeException("Create directory failure");
                    }
                    try {
                        sftp.cd(dir);
                        log.debug("Change directory {}", dir);
                    } catch (SftpException e1) {
                        log.error("Change directory failure, directory:{}", dir, e1);
                        throw new RuntimeException("Change directory failure");
                    }
                }
            }
        }
    }
}
