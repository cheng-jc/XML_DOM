package xml.dom;
import java.util.Scanner;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
public class practice_stu {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		StudentService studentservice = new StudentService();
		Document doc = studentservice.getDocument("src/classes.xml");
		//显示操作界面
		System.out.println("查看成绩----view");
		System.out.println("按照id号修改----modify");
		System.out.println("按照id添加一个学生----add");
		System.out.println("保存信息----save");
		//获取用户希望干什么
		String operType=scanner.next();
		//选择对应的程序进行执行
		switch(operType) {
		case "view": {
			System.out.println("所有学生成绩");
			studentservice.showStudsFen();
			break;
		}
		case "modify": {
			System.out.println("修改id学生成绩");
			System.out.println("请输入id");
			int Id=Integer.parseInt(scanner.next());
			studentservice.modifyStu(Id);
			break;
		}
		case "add": {
			System.out.println("添加学生");
			System.out.println("请输入id添加学生");
			int Id=Integer.parseInt(scanner.next());
			System.out.println("请输入学生姓名");
			String name=scanner.next();
			System.out.println("请输入学生年龄");
			String age=scanner.next();
			System.out.println("请输入学生个人介绍");
			String intro=scanner.next();
			System.out.println("请输入学生课程成绩");
			String score=scanner.next();
			studentservice.addStu(Id,name,age,intro,score);
			break;
		}
		case "save" :{
			studentservice.saveMessage(doc);
			System.out.println("保存信息");
			break;
		}
		default: {
			System.out.println("保存信息");
			break;
		}
}
	}
}
//学生信息操作类
class StudentService{
	//显示所有学生成绩的方法
	public void showStudsFen() throws Exception{
		//得到dom树
		Document doc = getDocument("src/classes.xml");
		//得到学生信息
		NodeList nodeList = doc.getElementsByTagName("学生");
		//遍历输出学生信息
		for (int i=0;i<nodeList.getLength();i++) {
			Element element = (Element) nodeList.item(i);
			System.out.println("编号 "+ element.getAttribute("sid")+" 名字 "+getFirstElement(element,"名字")+"成绩  "+
			getFirstElement(element,"课程成绩"));
		}	
	}
	//修改指定id学生成绩的方法
	public void modifyStu(int idGiven) throws Exception{
		//得到dom树
		Document doc = getDocument("src/classes.xml");
		//得到学生信息
		NodeList nodeList =  doc.getElementsByTagName("学生");
		//遍历找到指定id学生并修改，再输出所有学生信息
		for (int i=0;i<nodeList.getLength();i++) {
			Element element = (Element) nodeList.item(i);
			int Id = Integer.parseInt(element.getAttribute("sid"));
			//找到指定id学生进行修改
			if (Id==idGiven) {
				Element elementScore = (Element) element.getElementsByTagName("课程成绩").item(0);
				elementScore.setTextContent("100");
			}
			saveMessage(doc);
			System.out.println("编号 "+element.getAttribute("sid")+" 名字 "+getFirstElement(element,"名字")+" 成绩  "+
			getFirstElement(element,"课程成绩"));
		}	
	}
	//添加指定id学生的方法
	public void addStu(int idGiven,String nameGive,String ageGive,String introGive,String scoreGive) throws Exception{
		//得到dom树
		Document doc = getDocument("src/classes.xml");
		//添加学生信息
		Element newStu=doc.createElement("学生");
		newStu.setAttribute("sid", "idGiven");
		Element newStu_name=doc.createElement("名字");
		newStu_name.setTextContent(nameGive);
		Element newStu_age=doc.createElement("年龄");
		newStu_age.setTextContent(ageGive);
		Element newStu_intro=doc.createElement("介绍");
		newStu_intro.setTextContent(introGive);
		Element newStu_score=doc.createElement("课程");
		Element newStu_score2=doc.createElement("课程成绩");
		newStu_score2.setTextContent(scoreGive);
		//把子元素添加到父元素下
		newStu.appendChild(newStu_name);
		newStu.appendChild(newStu_age);
		newStu.appendChild(newStu_intro);
		newStu.appendChild(newStu_score);
		newStu_score.appendChild(newStu_score2);
		//把新的学生节点添加到根元素
		doc.getDocumentElement().appendChild(newStu);
		saveMessage(doc);
		NodeList nodeList = doc.getElementsByTagName("学生");
		for (int i=0;i<nodeList.getLength();i++) {
			Element element = (Element) nodeList.item(i);
			System.out.println("编号 "+ element.getAttribute("sid")+" 名字 "+getFirstElement(element,"名字")+"成绩  "+
			getFirstElement(element,"课程成绩"));
		}
	}	
	//保存修改的方法
	public void saveMessage(Document doc) throws Exception{
			//得到TransformerFactory
			TransformerFactory tff=TransformerFactory.newInstance();
			//通过TransformerFactory 得到一个转换器
			Transformer tf=tff.newTransformer();
			tf.transform(new DOMSource(doc), new StreamResult("src/classes.xml"));
	}
	//输出内容的方法
	public String getFirstElement(Element element,String name) {
		return element.getElementsByTagName(name).item(0).getTextContent();
	}
	//得到dom树的方法
	public Document getDocument (String url) throws Exception {
		//得到DocumentBuilderFactory
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		//得到DocumentBuilder
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		//指定文件
		return documentBuilder.parse(url);
	}
}

