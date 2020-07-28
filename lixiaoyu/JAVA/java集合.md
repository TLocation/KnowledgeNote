# 数据结构

###### 十进制转二进制

![1595867838201](C:\Users\28768\AppData\Roaming\Typora\typora-user-images\1595867838201.png)

###### 二进制转10进制

![1595868021125](C:\Users\28768\AppData\Roaming\Typora\typora-user-images\1595868021125.png)



\>>表示右移，如果该数为正，则高位补0，若为负数，则高位补1；

\>>>表示无符号右移，也叫逻辑右移，即若该数为正，则高位补0，而若该数为负数，则右移后高位同样补0。





## 数组

> ```
> char[] ch = new char[] {'l','x','y','a','n'};
> System.out.println(ch[5]);
> 
> //会出现 索引越界异常本来就5个，下标最大是4
> Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 5
> ```
>
> 特点：
>
> ​	1.内存地址连续，使用之前必须要制定数组长度。
>
> ​	2.可以通过下标访问的方式访问成员，查询效率比较高。
>
> ​	3.增删操作会带来性能消耗。(如果想在一个数组上再加一个数据，那么就得继续扩容：数组的扩容就是重新创建一个下标是5的数组 把原来的拿过来 再在最后面添加上第六个)



## 链表

###### 双向链表

> ```
> LinkedList 源码 中的内部类
> 
> private static class Node<E> {
>         E item; //节点元素
>         Node<E> next;//下一个节点
>         Node<E> prev;//上一个节点
> 
>         Node(Node<E> prev, E element, Node<E> next) {
>             this.item = element;
>             this.next = next;
>             this.prev = prev;
>         }
>     }
> ```
>
> 特点:
>
> ​	1.灵活的空间要求，存储空间不要求连续。
>
> ​	2.不支持下标访问，支持顺序遍历检索。
>
> ​	3.针对增删效果更高一些，只和操作节点的前后节点有关系，无需移动元素。



## 树

###### 红黑树

> 红黑树Red-Black-Tree[RBT] 是一个**自平衡**【不是绝对的】的二叉树。树上的节点需满足如下规则。
>
> 1.每个节点要么是红色，要么是黑色。
>
> 2.根节点必须是黑色。
>
> 3.每个叶子节点【NIL】是黑色。
>
> 4.每个红色节点的两个子节点必须是黑色。
>
> 5.任意节点到每个叶子节点的路劲包含相同数量的黑节点。
>
> 





## 集合

###### Collection 接口：







## List接口

### ArrayList 

> 本质就是动态数组,动态扩容

```
	/**
     * Default initial capacity.
     //默认的数组长度
     */
	private static final int DEFAULT_CAPACITY = 10;
	

    /**
     * Shared empty array instance used for empty instances.
     //空数组
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     //集合中存储数据的  数组对象
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer. Any
     * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * will be expanded to DEFAULT_CAPACITY when the first element is added.
     */
    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * The size of the ArrayList (the number of elements it contains).
     *
     * @serial
     //集合中元素的个数
     */
    private int size;
```

#### 初始化操作

> 无参构造
>
> ```
>     public ArrayList() {
>         this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
>     }
> ```
>

##### add添加源码分析

> 第一次添加

```
public boolean add(E e) {
		//确定容量  动态扩容  size初始0 
        ensureCapacityInternal(size + 1);  // ensureCapacityInternal(1);
        //将 要添加的元素添加到数组中   
        elementData[size++] = e;
        return true;
    }
```

```
private void ensureCapacityInternal(int minCapacity) { //1
		//ensureExplicitCapacity(10)
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
 }
 
//elementData {} 为空    mincapacity为1
private static int calculateCapacity(Object[] elementData, int minCapacity) {
        //如果elementdata == {}空
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        	//DEFAULT_CAPACITY 默认10
        	//返回下面2个参数的最大值
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        //不等于空返回
        return minCapacity;
 }

```

```
private void ensureExplicitCapacity(int minCapacity) { //10
        modCount++; // 自增长

        // 10 - 0
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
```

```java
private void grow(int minCapacity) { // 10
        // oldCapacity 0
        int oldCapacity = elementData.length; // 0
    	//newCapacity 0
        int newCapacity = oldCapacity + (oldCapacity >> 1);// 向右移动一位
        // 0 - 10 
    	if (newCapacity - minCapacity < 0)
            //newCapacity 10
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 返回一个新的长度数组
    	//elementData {} 付给了 newCapacity {,,,,,,,,,}
        elementData = Arrays.copyOf(elementData, newCapacity);
}
```

> 第二次添加

```java
public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // 2
        elementData[size++] = e;
        return true;
}
```

```java
private void ensureCapacityInternal(int minCapacity) { // 2
    	// ensureExplicitCapacity(10,2)
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
}

private static int calculateCapacity(Object[] elementData, int minCapacity) {
    	// 10 == {}
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
    	//无效返回 2
        return minCapacity;
    }
```

```java
private void ensureExplicitCapacity(int minCapacity) { //2
        modCount++;

        // 2 - 10 不成立 继续往下执行
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

```

```java
public boolean add(E e) { 
        ensureCapacityInternal(size + 1);  // 2
        elementData[size++] = e; // elementData[2] = e;
        return true;
    }
```

> 第11次添加

```java
public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // 12
        elementData[size++] = e;
        return true;
    }

```

```java
private void ensureCapacityInternal(int minCapacity) { //12
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

//参数是     10 12
private static int calculateCapacity(Object[] elementData, int minCapacity) { 
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
    	//返回12
        return minCapacity;
    }
```

```java
private void ensureExplicitCapacity(int minCapacity) { //12
        modCount++;

        // 12 - 10  > 0
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

private void grow(int minCapacity) { //12
        // oldCapacity 10
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1); // 10 + 5
    	// 15 - 10
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
    	// MAX_ARRAY_SIZE 是指integer的最大值  这个地方始终都是小于0
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        //elementData 10 {1,2,3,4,5,6,7,8,9,10}
    	//newCapacity 15 {1,2,3,4,5,6,7,8,9,10,,,,,}
    	//也就是在原来的基础上复制到另一个新的数组中
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // 12
        elementData[size++] = e; elementData[12] = e;
        return true;
    }

```



##### get获取源码分析

```java
public E get(int index) { // 1
    	//检查下标是否合法
        rangeCheck(index);
		
    	//返回指定下标处的数据
        return elementData(index);
    }

private void rangeCheck(int index) {
        if (index >= size) // 下标如果大于 长度
            //就抛出异常索引越界
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
```



##### set获取源码分析

```java
// 下标为1     数据 11
public E set(int index, E element) { // 1   11
    //检查下标是否合法 否则抛出索引越界    
    rangeCheck(index);
		//获取到索引处数据
        E oldValue = elementData(index);
    	//把索引处数据进行赋值
        elementData[index] = element;
    	//返回原来的数据
        return oldValue;
    }
```



##### remove获取源码分析(数组长度不变)

```java
public E remove(int index) { // 1
        //检查索引是否合法
    	rangeCheck(index);

        modCount++;
    	//获取到索引处数据
        E oldValue = elementData(index);
		//15 - 1 - 1 = 13
        int numMoved = size - index - 1;
    	//13 > 0
        if (numMoved > 0)
            //源数组{1,2,3,4,5,6,7,8,9}
            //目标数组{1,3,4,5,6,7,8,9,null}
            //这边有一个这样的操作 就是覆盖掉了
            // 源数组  开始下标   目标数组 开始下标  长度
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // 把原来的数组 下标为 14的赋值为null
		//返回删除的数据
        return oldValue;
    }
```



##### FailFast机制

​	快速失败的机制，java集合类为了应对并发访问在集合迭代过程中，内部结构发生变化的一种防护措施，这种错误检查的机制为这种可能发生错误通过抛出java.util.ConcurrentModificationException .

实例：

```java
// 创建2个线程类
public class ThreadIterator extends Thread {

    private List list;

    public ThreadIterator(List list){
        this.list = list;
    }

    @Override
    public void run() {
        while (true){
            for (Iterator iteratorTmp = list.iterator();iteratorTmp.hasNext();){
                iteratorTmp.next();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


public class ThreadAdd extends Thread{

    private List list;

    public ThreadAdd(List list){
        this.list = list;
    }



    @Override
    public void run() {
        for (int i = 0; i < 100 ; i++){
            System.out.println("循环执行"+i);
            try {
                Thread.sleep(5);
                list.add(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
```

```java
//测试类
public class JavaTest {

    private static List list = new ArrayList();

    public static void main(String[] args) {
        
        new ThreadIterator(list).start();
        new ThreadAdd(list).start();

    }
}

```

![1595866443956](C:\Users\28768\AppData\Roaming\Typora\typora-user-images\1595866443956.png)

![1595866558512](C:\Users\28768\AppData\Roaming\Typora\typora-user-images\1595866558512.png)

![1595866582549](C:\Users\28768\AppData\Roaming\Typora\typora-user-images\1595866582549.png)

![1595866618760](C:\Users\28768\AppData\Roaming\Typora\typora-user-images\1595866618760.png)

2条线程导致 modCount != expectedModCount 就会报错



> 有参构造
>
> ```
> public ArrayList(int initialCapacity) {
>         if (initialCapacity > 0) {
>         	//出事长度大于0 就创建制定大小的数组
>             this.elementData = new Object[initialCapacity];
>         } else if (initialCapacity == 0) {
>         	//否则就赋值一个空数组
>             this.elementData = EMPTY_ELEMENTDATA;
>         } else {
>         	//否则抛出异常
>             throw new IllegalArgumentException("Illegal Capacity: "+
>                                                initialCapacity);
>         }
>     }
> ```
>



#### LinkedList

LinkedList是通过双向链表去实现的。他的数据结构具有双向链表的优缺点，既然是双向链表，那么他的顺序访问效率会非常高，而且随机访问的效率会比较低，它包含一个非常重要的私有内部静态类：node；

```java
private static class Node<E> {
        E item;//节点的元素
        Node<E> next;//下一个节点
        Node<E> prev;//上一个节点

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
```

```java
public void push(E e) { //push往链表的头部去加
        addFirst(e);
    }

public void addFirst(E e) {
        linkFirst(e);
    }

private void linkFirst(E e) {
        final Node<E> f = first; //头部
        final Node<E> newNode = new Node<>(null, e, f);//把我们的数据e放在头部
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
        modCount++;
    }
```

```java
public void add(int index, E element) { //add 从尾部去加
        checkPositionIndex(index);

        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }

void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
    }

```



get方法:本质上还是便利

```java
public E get(int index) {//获取对应下标的值
        checkElementIndex(index);
        return node(index).item;
    }
    
Node<E> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {//如果index < 长度的一般
            Node<E> x = first; //从头开始循环
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last; // 从尾部开始循环
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
```

set方法：

```java
public E set(int index, E element) {
        checkElementIndex(index);//检查下标是否合法
        Node<E> x = node(index);//根据下标获取node对象
        E oldVal = x.item;//记录原来的值
        x.item = element;//赋予新的值
        return oldVal;//返回修改之前的值
    }
```

#### Vector

和ArrayList很相似，都是以动态数组的形式存储数据。

vector是线程安全的。

每个方法都有加synchronized关键字所修饰。对性能有比较大的影响慢慢就被淘汰了。



Collections

```java
collecitons中的synchronized可以增加代码的灵活度，需要同步的时候就通过下面的代码实现

List list1 = Collections.synchronizedList(list);

```



## set接口

### 1.HashSet

>概述:
>
>​		HashSet 实现set接口，由哈希表支持，他不保证set的迭代顺序，特别是他不保证该顺序永久不变。允许使用null作为元素。
>
>特点:
>
>​		底层数据结构是哈希表，HashSet的本质是一个“没有重复元素的集合”，他是通过`HashMap`实现的HashSet中包含一个HashMap类型的成员变量`map`.

```java
private transient HashMap<E,Object> map; //成员变量map

public HashSet() {//无参构造器
        map = new HashMap<>();
    }

```



add方法：本质上试讲数据保持在HashMap中，key是我们添加的内容，value就是我们定义的一个object对象。

```java
public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }
```





### 2.TreeSet

> 概述：
>
> ​		基于TreeMap的NavigableSet实现，使用元素的自然顺序进行排序，后者根据创建set时提供的Comparator进行排序，具体取决于使用的构造方法。
>
> 
>
> 本质是将数据保存在TreeMap中，key是我们添加的内容，value是定义的一个object对象。

```java
public TreeSet() {
        this(new TreeMap<E,Object>());
    }
```



## Map 接口：

## Iterator 迭代：

## 工具类：

​	Collections：

​	Arrays：

## 比较器：

​	Comparable  Comparator

















