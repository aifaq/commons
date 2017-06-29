/*
 *  Copyright 2011 sunli [sunli1223@gmail.com][weibo.com@sunli1223]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.google.code.fqueue.log;

import com.google.code.fqueue.exception.FileFormatException;
import me.aifaq.commons.lang.SunUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 数据索引文件
 * 
 * @author sunli
 * @date 2011-5-18
 * @version $Id: LogIndex.java 2 2011-07-31 12:25:36Z sunli1223@gmail.com $
 */
public class LogIndex {
	private static final Logger logger = Logger.getLogger(LogIndex.class.getName());
	private final int dbFileLimitLength = 32;
	private RandomAccessFile dbRandFile = null;
	private FileChannel fc;
	private MappedByteBuffer mappedByteBuffer;

	/**
	 * 文件操作位置信息
	 */
	private String magicString = null;
	private int version = -1;
	private int readerPosition = -1;
	private int writerPosition = -1;
	private int readerIndex = -1;
	private int writerIndex = -1;
	private AtomicInteger size = new AtomicInteger();

	public LogIndex(String path) throws IOException, FileFormatException {
		File dbFile = new File(path);

		// 文件不存在，创建文件
		if (dbFile.exists() == false) {
			dbFile.createNewFile();
			dbRandFile = new RandomAccessFile(dbFile, "rwd");
			dbRandFile.write(LogEntity.MAGIC.getBytes());// magic
			dbRandFile.writeInt(1);// 8version
			dbRandFile.writeInt(LogEntity.messageStartPosition);// 12 reader
			// pos
			dbRandFile.writeInt(LogEntity.messageStartPosition); // 16write
			// pos
			dbRandFile.writeInt(1);// 20readerindex
			dbRandFile.writeInt(1);// 24writerindex
			dbRandFile.writeInt(0);// 28 size
			magicString = LogEntity.MAGIC;
			version = 1;
			readerPosition = LogEntity.messageStartPosition;
			writerPosition = LogEntity.messageStartPosition;
			readerIndex = 1;
			writerIndex = 1;
		} else {
			dbRandFile = new RandomAccessFile(dbFile, "rwd");
			if (dbRandFile.length() < 32) {
				throw new FileFormatException("file format error, please check file:" + path);
			}
			byte[] b = new byte[this.dbFileLimitLength];
			dbRandFile.read(b);
			ByteBuffer buffer = ByteBuffer.wrap(b);
			b = new byte[LogEntity.MAGIC.getBytes().length];
			buffer.get(b);
			magicString = new String(b);
			version = buffer.getInt();
			readerPosition = buffer.getInt();
			writerPosition = buffer.getInt();
			readerIndex = buffer.getInt();
			writerIndex = buffer.getInt();
			size.set(buffer.getInt());

		}
		fc = dbRandFile.getChannel();
		mappedByteBuffer = fc.map(MapMode.READ_WRITE, 0, this.dbFileLimitLength);
	}

	/**
	 * 记录写位置
	 * 
	 * @param pos
	 */
	public void putWriterPosition(int pos) {
		mappedByteBuffer.position(16);
		mappedByteBuffer.putInt(pos);
		this.writerPosition = pos;
	}

	/**
	 * 记录读取的位置
	 * 
	 * @param pos
	 */
	public void putReaderPosition(int pos) {
		mappedByteBuffer.position(12);
		mappedByteBuffer.putInt(pos);
		this.readerPosition = pos;
	}

	/**
	 * 记录写文件索引
	 * 
	 * @param index
	 */
	public void putWriterIndex(int index) {
		mappedByteBuffer.position(24);
		mappedByteBuffer.putInt(index);
		this.writerIndex = index;
	}

	/**
	 * 记录读取文件索引
	 * 
	 * @param index
	 */
	public void putReaderIndex(int index) {
		mappedByteBuffer.position(20);
		mappedByteBuffer.putInt(index);
		this.readerIndex = index;
	}

	public void incrementSize() {
		int num = size.incrementAndGet();
		mappedByteBuffer.position(28);
		mappedByteBuffer.putInt(num);
	}

	public void decrementSize() {
		int num = size.decrementAndGet();
		mappedByteBuffer.position(28);
		mappedByteBuffer.putInt(num);
	}

	public String getMagicString() {
		return magicString;
	}

	public int getVersion() {
		return version;
	}

	public int getReaderPosition() {
		return readerPosition;
	}

	public int getWriterPosition() {
		return writerPosition;
	}

	public int getReaderIndex() {
		return readerIndex;
	}

	public int getWriterIndex() {
		return writerIndex;
	}

	public int getSize() {
		return size.get();
	}

	/**
	 * 关闭索引文件
	 */
	public void close() {
		try {
			mappedByteBuffer.force();
			SunUtil.cleanPrivileged(mappedByteBuffer, "cleaner");
			fc.close();
			dbRandFile.close();
			mappedByteBuffer = null;
			fc = null;
			dbRandFile = null;
		} catch (IOException e) {
			logger.log(Level.SEVERE, "close logindex file error:", e);
		}
	}

	public String headerInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(" magicString:");
		sb.append(magicString);
		sb.append(" version:");
		sb.append(version);
		sb.append(" readerPosition:");
		sb.append(readerPosition);
		sb.append(" writerPosition:");
		sb.append(writerPosition);
		sb.append(" size:");
		sb.append(size);
		sb.append(" readerIndex:");
		sb.append(readerIndex);
		sb.append(" writerIndex:");
		sb.append(writerIndex);
		return sb.toString();
	}

}
