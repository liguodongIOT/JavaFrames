Java NIO(New IO)是一个可以替代标准Java IO API的IO API（从Java 1.4开始)，
Java NIO提供了与标准IO不同的IO工作方式。

### Java NIO: Channels and Buffers（通道和缓冲区）
标准的IO基于字节流和字符流进行操作的，而NIO是基于通道（Channel）和缓冲区（Buffer）进行操作，
数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中。

### Java NIO: Non-blocking IO（非阻塞IO）
Java NIO可以让你非阻塞的使用IO，例如：当线程从通道读取数据到缓冲区时，线
程还是可以进行其他事情。当数据被写入到缓冲区时，线程可以继续处理它。从缓冲区写入通道也类似。

### Java NIO: Selectors（选择器）
Java NIO引入了选择器的概念，选择器用于监听多个通道的事件（比如：连接打开，数据到达）。
因此，单个的线程可以监听多个数据通道。


-------------------------------------------------------------------------------------------
### Java NIO 概述
Java NIO 由以下几个核心部分组成：

Channels,
Buffers,
Selectors

#### Channel 和 Buffer

基本上，所有的 IO 在NIO 中都从一个Channel 开始。Channel 有点像流。
数据可以从Channel读到Buffer中，也可以从Buffer 写到Channel中。

Channel和Buffer有好几种类型。下面是JAVA NIO中的一些主要Channel的实现：

FileChannel，
DatagramChannel，
SocketChannel，
ServerSocketChannel，

这些通道涵盖了UDP 和 TCP 网络IO，以及文件IO。

以下是Java NIO里关键的Buffer实现：

ByteBuffer，
CharBuffer，
DoubleBuffer，
FloatBuffer，
IntBuffer，
LongBuffer，
ShortBuffer，

这些Buffer覆盖了你能通过IO发送的基本数据类型：byte, short, int, long, float, double 和 char。

Java NIO 还有个 MappedByteBuffer，用于表示内存映射文件。

#### Selector

Selector允许单线程处理多个 Channel。如果你的应用打开了多个连接（通道），
但每个连接的流量都很低，使用Selector就会很方便。例如，在一个聊天服务器中。

要使用Selector，得向Selector注册Channel，然后调用它的select()方法。
这个方法会一直阻塞到某个注册的通道有事件就绪。一旦这个方法返回，线程就可以处理这些事件，
事件的例子有如新连接进来，数据接收等。

-------------------------------------------------------------------------------------------
### Channel

Java NIO的通道类似流，但又有些不同：

既可以从通道中读取数据，又可以写数据到通道。但流的读写通常是单向的。
通道可以异步地读写。
通道中的数据总是要先读到一个Buffer，或者总是要从一个Buffer中写入。


#### Channel的实现

这些是Java NIO中最重要的通道的实现：

FileChannel，
DatagramChannel，
SocketChannel，
ServerSocketChannel

FileChannel 从文件中读写数据。

DatagramChannel 能通过UDP读写网络中的数据。

SocketChannel 能通过TCP读写网络中的数据。

ServerSocketChannel 可以监听新进来的TCP连接，像Web服务器那样。
对每一个新进来的连接都会创建一个SocketChannel。


-------------------------------------------------------------------------------------------

### Buffer

Java NIO中的Buffer用于和NIO通道进行交互。如你所知，数据是从通道读入缓冲区，
从缓冲区写入到通道中的。

缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，
并提供了一组方法，用来方便的访问该块内存。


#### Buffer的基本用法
使用Buffer读写数据一般遵循以下四个步骤：

写入数据到Buffer

调用flip()方法

从Buffer中读取数据

调用clear()方法或者compact()方法


当向buffer写入数据时，buffer会记录下写了多少数据。一旦要读取数据，
需要通过flip()方法将Buffer从写模式切换到读模式。在读模式下，
可以读取之前写入到buffer的所有数据。

一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。
有两种方式能清空缓冲区：调用clear()或compact()方法。clear()方法会清空整个缓冲区。
compact()方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，
新写入的数据将放到缓冲区未读数据的后面。


#### Buffer的capacity,position和limit

缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。
这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。

为了理解Buffer的工作原理，需要熟悉它的三个属性：
capacity,
position,
limit,

position和limit的含义取决于Buffer处在读模式还是写模式。
不管Buffer处在什么模式，capacity的含义总是一样的。


**capacity**

作为一个内存块，Buffer有一个固定的大小值，也叫“capacity”.
你只能往里写capacity个byte、long，char等类型。一旦Buffer满了，需要将其清空
（通过读数据或者清除数据）才能继续往里写数据。

**position**

当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.
当一个byte、long等数据写到Buffer后， position会向前移动到下一个可插入数据的Buffer单元。
position最大可为capacity – 1.

当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0.
当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。

**limit**

在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。
写模式下，limit等于Buffer的capacity。

当切换Buffer到读模式时， limit表示你最多能读到多少数据。
因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。
换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，
这个值在写模式下就是position）

#### Buffer的类型

Java NIO 有以下Buffer类型:
ByteBuffer,
MappedByteBuffer,
CharBuffer,
DoubleBuffer,
FloatBuffer,
IntBuffer,
LongBuffer,
ShortBuffer

如你所见，这些Buffer类型代表了不同的数据类型。
换句话说，就是可以通过char，short，int，long，float 或 double类型来操作缓冲区中的字节。

MappedByteBuffer 有些特别。

#### Buffer的分配

要想获得一个Buffer对象首先要进行分配。 每一个Buffer类都有一个allocate方法。


下面是一个分配48字节capacity的ByteBuffer的例子。
```
ByteBuffer buf = ByteBuffer.allocate(48);
```

这是分配一个可存储1024个字符的CharBuffer：
```
CharBuffer buf = CharBuffer.allocate(1024);
```

#### 向Buffer中写数据

写数据到Buffer有两种方式：
从Channel写到Buffer。
通过Buffer的put()方法写到Buffer里。

从Channel写到Buffer的例子
```
int bytesRead = inChannel.read(buf); //read into buffer.
```

通过put方法写Buffer的例子：
```
buf.put(127);
```

put方法有很多版本，允许你以不同的方式把数据写入到Buffer中。
例如， 写到一个指定的位置，或者把一个字节数组写入到Buffer。

#### **flip()方法**

flip方法将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，
并将limit设置成之前position的值。

换句话说，position现在用于标记读的位置，limit表示之前 写 进了多少个byte、char等
—— 现在能读取多少个byte、char等。


#### 从Buffer中读取数据

从Buffer中读取数据有两种方式：
从Buffer读取数据到Channel。
使用get()方法从Buffer中读取数据。

从Buffer读取数据到Channel的例子：
```
//read from buffer into channel.
int bytesWritten = inChannel.write(buf);
```

使用get()方法从Buffer中读取数据的例子：
```
byte aByte = buf.get();
```

get方法有很多版本，允许你以不同的方式从Buffer中读取数据。
例如，从指定position读取，或者从Buffer中读取数据到字节数组。

**rewind()方法**

Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。
limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。

#### **clear()与compact()方法**

一旦读完Buffer中的数据，需要让Buffer准备好再次被写入。
可以通过clear()或compact()方法来完成。

如果调用的是clear()方法，position将被设回0，limit被设置成 capacity的值。
换句话说，Buffer 被清空了。Buffer中的数据并未清除，
只是这些标记告诉我们可以从哪里开始往Buffer里写数据。

如果Buffer中有一些未读的数据，调用clear()方法，数据将“被遗忘”，
意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有。

如果Buffer中仍有未读的数据，且后续还需要这些数据，
但是此时想要先写些数据，那么使用compact()方法。

compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。
limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，
但是不会覆盖未读的数据。

#### **mark()与reset()方法**

通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。
之后可以通过调用Buffer.reset()方法恢复到这个position。例如：
```
buffer.mark();

//call buffer.get() a couple of times, e.g. during parsing.
buffer.reset();  //set position back to mark.
```

#### **equals()与compareTo()方法**

可以使用equals()和compareTo()方法比较两个Buffer。

**equals()方法**

当满足下列条件时，表示两个Buffer相等：

有相同的类型（byte、char、int等）。

Buffer中剩余的byte、char等的个数相等。

Buffer中所有剩余的byte、char等都相同。

如你所见，equals只是比较Buffer的一部分，不是每一个在它里面的元素都比较。
实际上，它只比较Buffer中的剩余元素。


**compareTo()方法**

compareTo()方法比较两个Buffer的剩余元素(byte、char等)， 如果满足下列条件，
则认为一个Buffer“小于”另一个Buffer：

第一个不相等的元素小于另一个Buffer中对应的元素 。
所有元素都相等，但第一个Buffer比另一个先耗尽(第一个Buffer的元素个数比另一个少)。



-------------------------------------------------------------------------------------------




-------------------------------------------------------------------------------------------





-------------------------------------------------------------------------------------------


