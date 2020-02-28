package com.unionman.springbootsecurityauth2.utils;

import com.unionman.springbootsecurityauth2.entity.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.System;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class UDPTest {
	private static int PORT = 0;// "50005“"8081"
	private static String HOSTNAME = "";// "127.0.0.1""192.168.2.108"

	public static void main(String[] args) {
		getIpAndPort();
	}
	
	public static void getIpAndPort() {
		Properties props = new Properties();
		try {
			Map<String,String> map = new HashMap<String,String>();
			ClassLoader classLoader = UDPTest.class.getClassLoader();
	        InputStream in = classLoader.getResourceAsStream("udpConfig.properties");
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				map.put(key, Property);
			}
			HOSTNAME = map.get("ip");
			PORT = Integer.parseInt(map.get("port"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(PORT+" "+HOSTNAME);
		

	}

	public static void userUDP(String userId,int action){
		//action 1 新增 2 修改 3 删除
		byte[] commandId = new byte[2];
		commandId[0] = 1;
		commandId[1] = 2;
		PublicCallProtobuf.STDbUserSync.Builder pub = PublicCallProtobuf.STDbUserSync
				.newBuilder();
		pub.setOperation(action);
		pub.setUserId(userId);
		byte[] content = pub.build().toByteArray();
		sendOperation(commandId, content);
	}

	public static void groupUDP(String groupId,int action){
		//action 1 新增 2 修改 3 删除
		byte[] commandId = new byte[2];
		commandId[0] = 1;
		commandId[1] = 3;
		PublicCallProtobuf.STDbGroupSync.Builder pub = PublicCallProtobuf.STDbGroupSync
				.newBuilder();
		pub.setGroupId(groupId);
		pub.setOperation(action);
		byte[] content = pub.build().toByteArray();
		sendOperation(commandId, content);
	}

	public static void bdUDP(String bdId,int action){
		byte[] commandId = new byte[2];
		commandId[0]=1;
		commandId[1]=5;
		PublicCallProtobuf.STDbBureauDirectionSync.Builder pub = PublicCallProtobuf.STDbBureauDirectionSync
				.newBuilder();
		pub.setBdId(bdId);
		pub.setOperation(action);
		byte[] content = pub.build().toByteArray();
		UDPTest.sendOperation(commandId,content);
	}

	public static void user2groupUDP(String userId,String groupId,int action){
		byte[] commandId = new byte[2];
		commandId[0]=1;
		commandId[1]=4;
		PublicCallProtobuf.STDbUser2GroupSync.Builder pub = PublicCallProtobuf.STDbUser2GroupSync
				.newBuilder();
		pub.setUserId(userId);
		pub.setGroupId(groupId);
		pub.setOperation(action);
		byte[] content = pub.build().toByteArray();
		sendOperation(commandId,content);
	}

	public static void group2bdUDP(String bdId,String groupId,int action){
		byte[] commandId = new byte[2];
		commandId[0] = 1;
		commandId[1] = 6;
		PublicCallProtobuf.STDbGroup2BdSync.Builder pub = PublicCallProtobuf.STDbGroup2BdSync
				.newBuilder();
		pub.setBdId(bdId);
		pub.setGroupId(groupId);
		pub.setOperation(action);
		byte[] content = pub.build().toByteArray();
		sendOperation(commandId, content);
	}

	public static void gpsUserUDP(int gpsUserId,int action){
		byte[] commandId = new byte[2];
		commandId[0] = 1;
		commandId[1] = 1;
		PublicCallProtobuf.STDbGpsUserSync.Builder pub = PublicCallProtobuf.STDbGpsUserSync
				.newBuilder();
		pub.setOperation(action);
		pub.setGpsUserId(gpsUserId);
		byte[] content = pub.build().toByteArray();
		sendOperation(commandId, content);
	}

	public static void sendOperation(byte[] commandId, byte[] content) {
		System.out.println(PORT+" "+HOSTNAME);
		// 传入0表示让操作系统分配一 个端口号
		try (DatagramSocket socket = new DatagramSocket(0)) {
			socket.setSoTimeout(10000);
			InetAddress host = InetAddress.getByName(HOSTNAME);
			byte[] header = new byte[4];
			// commandHeader
			header[0] = (byte) 0xC4;
			header[1] = (byte) 0xD7;
			// segNum
			header[2] = 1;
			// segFlag
			header[3] = 1;
			// length 后接数据长度 待定
			// commandId 256-0101
			// protocolNo
			byte[] body = new byte[6];
			body[0] = 0;
			body[1] = 0;
			// businessSN
			body[2] = 0;
			body[3] = 0;
			body[4] = 0;
			body[5] = 0;
			// srcDeviceId
			byte[] srcDeviceId = new byte[4];
			srcDeviceId[0] = 0;
			srcDeviceId[1] = 0;
			srcDeviceId[2] = 0;
			srcDeviceId[3] = 0;
			// dstDeviceId
			byte[] dstDeviceId = new byte[4];
			dstDeviceId[0] = 0;
			dstDeviceId[1] = 0;
			dstDeviceId[2] = 0;
			dstDeviceId[3] = 0;
			// content
			// length 后接数据长度
			byte[] length = new byte[2];
			length[0] = 0;
			length[1] = (byte) (content.length + 18);
			// System.out.println(content.length);
			// checksum
			byte[] checksum = new byte[2];
			checksum[0] = 0;
			checksum[1] = 0;
			byte[] temp1 = unitByteArray(header, length);
			byte[] temp2 = unitByteArray(temp1, commandId);
			byte[] temp3 = unitByteArray(temp2, body);
			byte[] temp4 = unitByteArray(temp3, srcDeviceId);
			byte[] temp5 = unitByteArray(temp4, dstDeviceId);
			byte[] temp6 = unitByteArray(temp5, content);
			byte[] temp7 = unitByteArray(temp6, checksum);

			// 指定包要发送的目的地
			DatagramPacket request = new DatagramPacket(temp7, temp7.length,
					host, PORT);
			// 为接受的数据包创建空间
			// DatagramPacket response = new DatagramPacket(new byte[1024],
			// 1024);
			System.out.println("temp7: "+temp7.toString()+" host: "+host+" PORT: "+PORT);
			socket.send(request);
			// socket.receive(response);
			// String result = new String(response.getData(), 0,
			// response.getLength(), "ASCII");
			// System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 合并byte数组
	 */
	public static byte[] unitByteArray(byte[] byte1, byte[] byte2) {
		byte[] unitByte = new byte[byte1.length + byte2.length];
		System.arraycopy(byte1, 0, unitByte, 0, byte1.length);
		System.arraycopy(byte2, 0, unitByte, byte1.length, byte2.length);
		return unitByte;
	}

	/**
	 * int 转 byte[4]
	 */
	public static byte[] intToByteArray(final int integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer
				: integer)) / 8;
		byte[] byteArray = new byte[4];

		for (int n = 0; n < byteNum; n++)
			byteArray[3 - n] = (byte) (integer >>> (n * 8));

		return (byteArray);
	}

}
