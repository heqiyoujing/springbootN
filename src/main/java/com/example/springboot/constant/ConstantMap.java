package com.example.springboot.constant;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author: yiqq
 * @date: 2018/9/28
 * @description: ImmutableMap 的作用就是：可以让java代码也能够创建一个对象常量映射，来保存一些常量映射的键值对。
 * 1.对不可靠的客户代码库来说，它使用安全，可以在未受信任的类库中安全的使用这些对象
 * 2.线程安全的：immutable对象在多线程下安全，没有竞态条件
 * 3.不需要支持可变性, 可以尽量节省空间和时间的开销. 所有的不可变集合实现都比可变集合更加有效的利用内存 (analysis)
 * 4.可以被使用为一个常量，并且期望在未来也是保持不变的
 */
public class ConstantMap {
    Map<String,String> stringMap = new ImmutableMap.Builder<String,String>()
            .put("0","同事")
            .put("1","校友")
            .put("2","同学")
            .build();
}
