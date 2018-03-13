package com.study.crud.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

/**
 * FreeMarker 快速入门:https://segmentfault.com/a/1190000011768799
 */
public class PdfGenerator {

    /**
     * @param basePath  项目根路径
     * @param variables 数据对象
     * @param fileName  pdf文件名称、模板名称
     * @param response
     */
    public static void printPDF(String basePath, Map<String, Object> variables,
                                String fileName, HttpServletResponse response) {
        // 1、创建freeMarker配置实例（FreeMarker jar 最新的版本（2.3.23）提示 Configuration 方法被弃用）
        Configuration cfg = new Configuration();
        try {
            // 2、获取模版路径
            cfg.setDirectoryForTemplateLoading(new File(basePath
                    + "/WEB-INF/ftl"));
            cfg.setDefaultEncoding("UTF-8");

            //3、 从上面指定的模板目录中加载对应的模板文件
            Template template = cfg.getTemplate(fileName + ".ftl");
            //4、 将生成的内容写入 letterTemplate.html中
            String file1 = basePath + "html/letterTemplate.html";
            File file = new File(file1);
            if (!file.exists()){
                file.createNewFile();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "utf-8"));
            //5、输出文件
            template.process(variables, out);
            out.flush();

            String url = new File(file1).toURI().toURL().toString();
            // PDF 文件的 MIME 类型
            response.setContentType("application/binary;charset=ISO8859_1");
            response.setContentType("application/pdf");
            response.setHeader("Content-type", "application/pdf");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String((fileName + ".pdf").getBytes(), "ISO8859_1"));
            ServletOutputStream os = response.getOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);

            // 解决中文问题
            //添加中文字库，也就是你的页面中用到的所有字体：页面中字体不能使用中文名称，需要使用英文名称，而且是大小写敏感的！
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(basePath + "/static/fonts/simsun.ttc",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
            os.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
