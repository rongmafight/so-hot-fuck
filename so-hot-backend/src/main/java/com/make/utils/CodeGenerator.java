package com.make.utils;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


/**
 * @Author make
 * @Description
 * @Date 2022/8/19 12:01
 */

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://124.222.93.56:3306/react_study", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("make") // 设置作者
                            .outputDir("E:\\study\\so-hot-fuck\\so-hot-backend\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.make"); // 设置父包
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_user_role", "sys_menu", "sys_role", "sys_role_menu", "sys_user") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_", "sys_")
                            // entity配置
                            .entityBuilder()
                            .enableLombok()
                            .addIgnoreColumns("created", "updated", "statu")

                            // Controller配置
                            .controllerBuilder()
                            .enableRestStyle();
                                ; // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}

