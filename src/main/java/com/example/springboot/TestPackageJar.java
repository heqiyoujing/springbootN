package com.example.springboot;

/**
 * @author: yiqq
 * @date: 2018/9/29
 * @description:
 */
public class TestPackageJar {
    /**
     * 打包jar，可直接运行
     *<plugins>
     <plugin>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-maven-plugin</artifactId>
     <version>1.5.6.RELEASE</version>
     <executions>
     <execution>
     <goals>
     <goal>repackage</goal>
     </goals>
     </execution>
     </executions>
     </plugin>
     <plugin>
     <groupId>org.apache.maven.plugins</groupId>
     <artifactId>maven-compiler-plugin</artifactId>
     <configuration>
     <source>1.8</source>
     <target>1.8</target>
     </configuration>
     </plugin>
     </plugins>
     */
    public static void main(String[] args) {
        System.out.println("1");
    }
}
