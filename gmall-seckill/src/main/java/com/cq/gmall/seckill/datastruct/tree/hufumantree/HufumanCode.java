package com.cq.gmall.seckill.datastruct.tree.hufumantree;


import java.io.*;
import java.util.*;

/**
 * @author 彭国仁
 * @data 2019/12/12 18:20
 */

//功能:根据赫夫曼编码压缩数据的原理,需要创建" ike likelike java do you like a java"对应的赫夫曼树
//        思路:
//        (1)Node{ datap存放数据, weight〈权值,left和rght}
//        (2)得到" i like likelike java do you like a java"对应的byte数组
//        ()编写一个方法,将准备构建赫夫曼树的Node节点放到ust,形式 odedate=97,wegt=5
//        Node[ldate=32, weight=9]…体现d1y1u:1j2v2o:24k4e:4i5a:59
//        (4)可以通过list创建对应的赫夫曼树

public class HufumanCode {

    public static void main(String[] args) {

        unZipFile("H:\\1.zip","H:\\4.jpg");
        System.out.println("解压成功");

       /* zipFile("H:\\2.jpg","H:\\2.zip");
        System.out.println("压缩文件ok~~");*/
       /* String strNode = "i like like like java do you like a java";
        //得到字节数组
        byte[] strNodeBytes = strNode.getBytes();
        //压缩
        byte[] bytes = huffmanZip(strNodeBytes);
        System.out.println(Arrays.toString(bytes));
        //解压
        byte[] decode = decode(huffmanCodes, bytes);
        System.out.println(new String(decode));*/
      /*  List<HereNode> list = getList(strNodeBytes);
        System.out.println(list);
        HereNode root = createHuffmanTree(list);
        preOrder(root);
        getCodes(root);
        System.out.println("~~" + map);
        byte[] zip = zip(strNodeBytes, map);
        System.out.println(Arrays.toString(zip));*/


    }


    //使用一个方法，将前面的方法封装起来，便于我们的调用.

    /**
     * @param bytes 原始的字符串对应的字节数组
     * @return 是经过 赫夫曼编码处理后的字节数组(压缩后的数组)
     */
    private static byte[] huffmanZip(byte[] bytes) {
        //每个字符和出现的次数放入list集合中
        List<HereNode> nodes = getList(bytes);
        //根据 nodes 创建的赫夫曼树
        HereNode huffmanTreeRoot = createHuffmanTree(nodes);
        //对应的赫夫曼编码(根据 赫夫曼树)
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        //根据生成的赫夫曼编码，压缩得到压缩后的赫夫曼编码字节数组
        byte[] huffmanCodeBytes = zip(bytes, huffmanCodes);
        return huffmanCodeBytes;
    }

    /**
     * 将字符封装成hereNode 对象放入list集合中
     *
     * @param nodeByte
     * @return
     */
    public static List<HereNode> getList(byte[] nodeByte) {
        List<HereNode> nodesList = new ArrayList<>();
        Map<Byte, Integer> map = new HashMap<>();
        for (Byte b : nodeByte) {
            if (map.get(b) == null) {
                map.put(b, 1);
            } else {
                map.put(b, map.get(b) + 1);
            }
        }
        for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
            nodesList.add(new HereNode(entry.getKey(), entry.getValue()));
        }

        return nodesList;
    }

    /**
     * 创建赫夫曼树
     *
     * @param list
     * @return
     */
    public static HereNode createHuffmanTree(List<HereNode> list) {
        while (list.size() > 1) {
            Collections.sort(list);
            HereNode left = list.get(0);
            HereNode right = list.get(1);
            HereNode parent = new HereNode(null, left.weight + right.weight);
            parent.left = left;
            parent.right = right;
            list.remove(left);
            list.remove(right);
            list.add(parent);
        }
        return list.get(0);
    }

    //前序遍历
    public static void preOrder(HereNode root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("赫夫曼树为空");
        }
    }

    //   生成赫夫曼树对应的赫夫曼编码
//思路
//1.将赫夫曼编码表存放在Map<Byte, String>形式
//生成的赫夫曼编码表{32=01,97=100,100=11000,117=11001,101=1110,118=11011,105=101,121=
    static Map<Byte, String> huffmanCodes = new HashMap<>();
    //2.在生成赫夫曼编码表示,需要去拼接路径,定义一个 StringBui1der存储某个叶子结点的路径
    static StringBuilder stringBuilder = new StringBuilder();

    /**
     * 生成赫夫曼编码表
     *
     * @param node
     * @param code
     * @param stringBuilder
     */
    public static void getCodes(HereNode node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder1 = new StringBuilder(stringBuilder);
        stringBuilder1.append(code);
        if (node.data == null) {
            //向左
            getCodes(node.left, "0", stringBuilder1);
            //向右
            getCodes(node.right, "1", stringBuilder1);
        } else {
            huffmanCodes.put(node.data, stringBuilder1.toString());
        }
    }

    public static Map<Byte, String> getCodes(HereNode node) {

        if (node != null) {
            getCodes(node, "", stringBuilder);
        } else {
            System.out.println("赫夫曼树为空");
        }
        return huffmanCodes;
    }


    //编写一个方法，将字符串对应的byte[] 数组，通过生成的赫夫曼编码表，返回一个赫夫曼编码 压缩后的byte[]

    /**
     * @param bytes        这时原始的字符串对应的 byte[]
     * @param huffmanCodes 生成的赫夫曼编码map
     * @return 返回赫夫曼编码处理后的 byte[]
     * 举例： String content = "i like like like java do you like a java"; =》 byte[] contentBytes = content.getBytes();
     * 返回的是 字符串 "1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100"
     * => 对应的 byte[] huffmanCodeBytes  ，即 8位对应一个 byte,放入到 huffmanCodeBytes
     * huffmanCodeBytes[0] =  10101000(补码) => byte  [推导  10101000=> 10101000 - 1 => 10100111(反码)=> 11011000= -88 ]
     * huffmanCodeBytes[1] = -88
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        //返回的是 字符串 "1010100010111111100100010111111110010001011
        StringBuilder sBuilder = new StringBuilder();
        for (byte b : bytes) {
            String s = huffmanCodes.get(b);
            sBuilder.append(s);
        }
        System.out.println(sBuilder);
        //对应的 byte[] huffmanCodeBytes  ，即 8位对应一个 byte,放入到 huffmanCodeBytes

        byte[] huffmanCodeBytes = new byte[(sBuilder.length() + 7) / 8];
        int index = 0;
        for (int i = 0; i < sBuilder.length(); i += 8) {
            String substring = "";
            if (i + 8 > sBuilder.length()) {
                substring = sBuilder.substring(i);
            } else {
                substring = sBuilder.substring(i, i + 8);
            }
            byte b = (byte) Integer.parseInt(substring, 2);
            huffmanCodeBytes[index] = b;
            index++;
        }
        return huffmanCodeBytes;
    }


    //完成数据的解压
    //思路
    //1. 将huffmanCodeBytes [-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
    //   重写先转成 赫夫曼编码对应的二进制的字符串 "1010100010111..."
    //2.  赫夫曼编码对应的二进制的字符串 "1010100010111..." =》 对照 赫夫曼编码  =》 "i like like like java do you like a java"


    //编写一个方法，完成对压缩数据的解码

    /**
     * @param huffmanCodes 赫夫曼编码表 map
     * @param huffmanBytes 赫夫曼编码得到的字节数组
     * @return 就是原来的字符串对应的数组
     */
    private static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        StringBuilder stringBuilder = new StringBuilder();
        //拼接二进制补码
        for (int i = 0; i < huffmanBytes.length; i++) {
            String strl = "";
            if (i == huffmanBytes.length - 1) {
                strl = byteToBitString(false, huffmanBytes[i]);
            } else {
                strl = byteToBitString(true, huffmanBytes[i]);
            }
            stringBuilder.append(strl);
        }
        //System.out.println(stringBuilder.toString());
        //把字符串安装指定的赫夫曼编码进行解码
        //把赫夫曼编码表进行调换，因为反向查询 a->100 100->a
        Map<String, Byte>  map = new HashMap<String,Byte>();
        for(Map.Entry<Byte, String> entry: huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }

        //创建要给集合，存放byte
        List<Byte> list = new ArrayList<>();
        //i 可以理解成就是索引,扫描 stringBuilder
        for(int  i = 0; i < stringBuilder.length(); ) {
            int count = 1; // 小的计数器
            boolean flag = true;
            Byte b = null;

            while(flag) {
                //1010100010111...
                //递增的取出 key 1
                String key = stringBuilder.substring(i, i+count);//i 不动，让count移动，指定匹配到一个字符
                b = map.get(key);
                if(b == null) {//说明没有匹配到
                    count++;
                }else {
                    //匹配到
                    flag = false;
                }
            }
            list.add(b);
            i += count;//i 直接移动到 count
        }
        //当for循环结束后，我们list中就存放了所有的字符  "i like like like java do you like a java"
        //把list 中的数据放入到byte[] 并返回
        byte b[] = new byte[list.size()];
        for(int i = 0;i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;
    }


    /**
     * 将一个byte 转成一个二进制的字符串, 如果看不懂，可以参考我讲的Java基础 二进制的原码，反码，补码
     *
     * @param b    传入的 byte
     * @param flag 标志是否需要补高位如果是true ，表示需要补高位，如果是false表示不补, 如果是最后一个字节，无需补高位
     * @return 是该b 对应的二进制的字符串，（注意是按补码返回）
     */
    private static String byteToBitString(boolean flag, byte b) {
        //使用变量保存 b
        int temp = b; //将 b 转成 int
        //如果是正数我们还存在补高位
        if (flag) {
            temp |= 256; //按位与 256  1 0000 0000  | 0000 0001 => 1 0000 0001
        }
        String str = Integer.toBinaryString(temp); //返回的是temp对应的二进制的补码
        if (flag) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }
    }

    //编写方法，将一个文件进行压缩
    /**
     *
     * @param srcFile 你传入的希望压缩的文件的全路径
     * @param dstFile 我们压缩后将压缩文件放到哪个目录
     */
    public static void zipFile(String srcFile, String dstFile) {

        //创建输出流
        OutputStream os = null;
        ObjectOutputStream oos = null;
        //创建文件的输入流
        FileInputStream is = null;
        try {
            //创建文件的输入流
            is = new FileInputStream(srcFile);
            //创建一个和源文件大小一样的byte[]
            byte[] b = new byte[is.available()];
            //读取文件
            is.read(b);
            //直接对源文件压缩
            byte[] huffmanBytes = huffmanZip(b);
            //创建文件的输出流, 存放压缩文件
            os = new FileOutputStream(dstFile);
            //创建一个和文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(os);
            //把 赫夫曼编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes); //我们是把
            //这里我们以对象流的方式写入 赫夫曼编码，是为了以后我们恢复源文件时使用
            //注意一定要把赫夫曼编码 写入压缩文件
            oos.writeObject(huffmanCodes);


        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                is.close();
                oos.close();
                os.close();
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //编写一个方法，完成对压缩文件的解压
    /**
     *
     * @param zipFile 准备解压的文件
     * @param dstFile 将文件解压到哪个路径
     */
    public static void unZipFile(String zipFile, String dstFile) {

        //定义文件输入流
        InputStream is = null;
        //定义一个对象输入流
        ObjectInputStream ois = null;
        //定义文件的输出流
        OutputStream os = null;
        try {
            //创建文件输入流
            is = new FileInputStream(zipFile);
            //创建一个和  is关联的对象输入流
            ois = new ObjectInputStream(is);
            //读取byte数组  huffmanBytes
            byte[] huffmanBytes = (byte[])ois.readObject();
            //读取赫夫曼编码表
            Map<Byte,String> huffmanCodes = (Map<Byte,String>)ois.readObject();

            //解码
            byte[] bytes = decode(huffmanCodes, huffmanBytes);
            //将bytes 数组写入到目标文件
            os = new FileOutputStream(dstFile);
            //写数据到 dstFile 文件
            os.write(bytes);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        } finally {

            try {
                os.close();
                ois.close();
                is.close();
            } catch (Exception e2) {
                // TODO: handle exception
                System.out.println(e2.getMessage());
            }

        }
    }
}

class HereNode implements Comparable<HereNode> {
    Byte data;
    Integer weight;
    HereNode left;
    HereNode right;

    public HereNode(Byte data, Integer weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "HereNode{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }


    @Override
    public int compareTo(HereNode o) {
        return this.weight - o.weight;
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}