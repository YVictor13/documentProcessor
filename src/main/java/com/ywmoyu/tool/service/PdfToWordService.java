package com.ywmoyu.tool.service;

import com.spire.doc.Document;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.widget.PdfPageCollection;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PdfToWordService {
    //设置当输入文件为大文件时候，需要切分，保存临时临时文件的路径
    // PDF 临时文件夹
    private static final String tempToPdfPath = "./pdf/";
    // Word 临时文件
    private static final String tempToWordPath = "./word/";

    // 默认下载路径为上传路径
    public String pdfToWord(String inputPath) {
        //生成 文件处理后的doc文件下载路径，默认是上传文件的路径下
        String downLoadPath = inputPath.substring(0, inputPath.length() - 4) + ".docx";
        // 设置 Boolean 变量判断当为大文件时候，处理是否成功
        boolean isSuccess = false;
        try {
            // 判断上传文件是否为合法文件
            boolean isPdf = isPdfFile(inputPath);
            if (isPdf) {
                // 加载PDF文件
                PdfDocument pdfDocument = new PdfDocument();
                pdfDocument.loadFromFile(inputPath);
                // 获取PDF文档的页数
                PdfPageCollection number = pdfDocument.getPages();

                if (number.getCount() > 11) {
                    //当当前PDF为大文件
                    // 1、创建临时pdf和word路径
                    boolean isCreate = CreateDir();
                    if (isCreate) {
                        // 2、将文件分割，每一页一张pdf
                        pdfDocument.split(tempToPdfPath + "tempPdf{0}.pdf", 0);

                        // 将其进行划分，然后将划分的PDF进行转换
                        File[] files = getSplitFiles(tempToPdfPath);
                        for (File file : files) {
                            PdfDocument pdf = new PdfDocument();
                            pdf.loadFromFile(file.getAbsolutePath());
                            pdf.saveToFile(tempToWordPath + file.getName().substring(0, file.getName().length() - 4) + ".docx", FileFormat.DOCX);
                        }
                        try {
                            isSuccess = MergeWordDocument(tempToWordPath, downLoadPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    pdfDocument.saveToFile(downLoadPath, com.spire.pdf.FileFormat.DOCX);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 将建立的临时文件夹删除掉
            if (isSuccess) {
                clearFiles(tempToPdfPath);
                clearFiles(tempToWordPath);
            }
        }

        return "";
    }

    // 合并分割、转换为word文档、将子word文件转换为word大文件
    private boolean MergeWordDocument(String tempToWordPath, String downLoadPath) {
        File[] files = getSplitFiles(tempToWordPath);

        Document document = new Document(tempToWordPath + "tempPdf0.docx");
        for (int i = 1; i < files.length; i++) {
            document.insertTextFromFile(tempToWordPath + "tempPdf" + i + ".docx", com.spire.doc.FileFormat.Docx_2013);
        }
        document.saveToFile(downLoadPath);
        return true;
    }

    // 获取到对应路径下所有的PDF
    private File[] getSplitFiles(String tempToPdfPath) {
        File file = new File(tempToPdfPath);
        File[] files = file.listFiles();
        if (files == null) {
            return null;
        }
        return files;
    }

    //删除临时文件夹
    private void clearFiles(String tempPath) {
        File file = new File(tempPath);
        if (file.exists()) {
            deleteFile(file);
        }

    }

    // 递归删除临时文件夹
    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                deleteFile(file1);
            }
        }
        file.delete();
    }

    // 创建word 和 pdf 分割为小文件时候，存储的临时文件
    private boolean CreateDir() {
        File pdfDir = new File(tempToPdfPath);
        File wordDir = new File(tempToWordPath);
        // 创建目录
        if (!pdfDir.exists()) {
            pdfDir.mkdirs();
        }
        if (!wordDir.exists()) {
            wordDir.mkdirs();
        }
        return true;
    }

    // 判断上传文件是否为PDF文件
    private boolean isPdfFile(String inputPath) {
        File file = new File(inputPath);
        // 获取文件的文件名（包含后缀）
        String fileName = file.getName();
        // 使用endsWith() 判断一个字符是否以某一个字符结尾
        if (fileName.endsWith(".pdf")) {
            return true;
        }
        return false;
    }


}
