# 项目
## 1、documentProcessor
>（documentProcessor）文件处理器。主要功能：实现PDF转换为Word、Word转换为PDF、PDF转换为PNG、Word转换为PNG..(后期更新PPT、Excel文档处理。。)
> 使用Springboot框架实现网页版的文档处理
## 2、帮助文档
> PDF 转换为 Word
> 由于使用第三方插件，Spire.doc 和 Spire.pdf ，它只支持将PDF前面11页PDF转化Word免费，多余11页的文档将无法处理
>解决方案：
>1、小于或等于11页的PDF直接转化为Word文档
>2、大于11页的PDF文档先将文档分割为11页小的PDF文档
```java
    /**
	* PDF转化为Word
	*/
	//1、加载PDF文件
	PdfDocument pdf = new PdfDocument();
	pdf.loadFromFile("Input.pdf");
	//2、保存为Word文件格式
	pdf.saveToFile("ToWord.docx",FileFormat.DOCX);

```


