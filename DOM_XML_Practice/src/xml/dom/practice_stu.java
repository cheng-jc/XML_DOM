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
		//��ʾ��������
		System.out.println("�鿴�ɼ�----view");
		System.out.println("����id���޸�----modify");
		System.out.println("����id���һ��ѧ��----add");
		System.out.println("������Ϣ----save");
		//��ȡ�û�ϣ����ʲô
		String operType=scanner.next();
		//ѡ���Ӧ�ĳ������ִ��
		switch(operType) {
		case "view": {
			System.out.println("����ѧ���ɼ�");
			studentservice.showStudsFen();
			break;
		}
		case "modify": {
			System.out.println("�޸�idѧ���ɼ�");
			System.out.println("������id");
			int Id=Integer.parseInt(scanner.next());
			studentservice.modifyStu(Id);
			break;
		}
		case "add": {
			System.out.println("���ѧ��");
			System.out.println("������id���ѧ��");
			int Id=Integer.parseInt(scanner.next());
			System.out.println("������ѧ������");
			String name=scanner.next();
			System.out.println("������ѧ������");
			String age=scanner.next();
			System.out.println("������ѧ�����˽���");
			String intro=scanner.next();
			System.out.println("������ѧ���γ̳ɼ�");
			String score=scanner.next();
			studentservice.addStu(Id,name,age,intro,score);
			break;
		}
		case "save" :{
			studentservice.saveMessage(doc);
			System.out.println("������Ϣ");
			break;
		}
		default: {
			System.out.println("������Ϣ");
			break;
		}
}
	}
}
//ѧ����Ϣ������
class StudentService{
	//��ʾ����ѧ���ɼ��ķ���
	public void showStudsFen() throws Exception{
		//�õ�dom��
		Document doc = getDocument("src/classes.xml");
		//�õ�ѧ����Ϣ
		NodeList nodeList = doc.getElementsByTagName("ѧ��");
		//�������ѧ����Ϣ
		for (int i=0;i<nodeList.getLength();i++) {
			Element element = (Element) nodeList.item(i);
			System.out.println("��� "+ element.getAttribute("sid")+" ���� "+getFirstElement(element,"����")+"�ɼ�  "+
			getFirstElement(element,"�γ̳ɼ�"));
		}	
	}
	//�޸�ָ��idѧ���ɼ��ķ���
	public void modifyStu(int idGiven) throws Exception{
		//�õ�dom��
		Document doc = getDocument("src/classes.xml");
		//�õ�ѧ����Ϣ
		NodeList nodeList =  doc.getElementsByTagName("ѧ��");
		//�����ҵ�ָ��idѧ�����޸ģ����������ѧ����Ϣ
		for (int i=0;i<nodeList.getLength();i++) {
			Element element = (Element) nodeList.item(i);
			int Id = Integer.parseInt(element.getAttribute("sid"));
			//�ҵ�ָ��idѧ�������޸�
			if (Id==idGiven) {
				Element elementScore = (Element) element.getElementsByTagName("�γ̳ɼ�").item(0);
				elementScore.setTextContent("100");
			}
			saveMessage(doc);
			System.out.println("��� "+element.getAttribute("sid")+" ���� "+getFirstElement(element,"����")+" �ɼ�  "+
			getFirstElement(element,"�γ̳ɼ�"));
		}	
	}
	//���ָ��idѧ���ķ���
	public void addStu(int idGiven,String nameGive,String ageGive,String introGive,String scoreGive) throws Exception{
		//�õ�dom��
		Document doc = getDocument("src/classes.xml");
		//���ѧ����Ϣ
		Element newStu=doc.createElement("ѧ��");
		newStu.setAttribute("sid", "idGiven");
		Element newStu_name=doc.createElement("����");
		newStu_name.setTextContent(nameGive);
		Element newStu_age=doc.createElement("����");
		newStu_age.setTextContent(ageGive);
		Element newStu_intro=doc.createElement("����");
		newStu_intro.setTextContent(introGive);
		Element newStu_score=doc.createElement("�γ�");
		Element newStu_score2=doc.createElement("�γ̳ɼ�");
		newStu_score2.setTextContent(scoreGive);
		//����Ԫ����ӵ���Ԫ����
		newStu.appendChild(newStu_name);
		newStu.appendChild(newStu_age);
		newStu.appendChild(newStu_intro);
		newStu.appendChild(newStu_score);
		newStu_score.appendChild(newStu_score2);
		//���µ�ѧ���ڵ���ӵ���Ԫ��
		doc.getDocumentElement().appendChild(newStu);
		saveMessage(doc);
		NodeList nodeList = doc.getElementsByTagName("ѧ��");
		for (int i=0;i<nodeList.getLength();i++) {
			Element element = (Element) nodeList.item(i);
			System.out.println("��� "+ element.getAttribute("sid")+" ���� "+getFirstElement(element,"����")+"�ɼ�  "+
			getFirstElement(element,"�γ̳ɼ�"));
		}
	}	
	//�����޸ĵķ���
	public void saveMessage(Document doc) throws Exception{
			//�õ�TransformerFactory
			TransformerFactory tff=TransformerFactory.newInstance();
			//ͨ��TransformerFactory �õ�һ��ת����
			Transformer tf=tff.newTransformer();
			tf.transform(new DOMSource(doc), new StreamResult("src/classes.xml"));
	}
	//������ݵķ���
	public String getFirstElement(Element element,String name) {
		return element.getElementsByTagName(name).item(0).getTextContent();
	}
	//�õ�dom���ķ���
	public Document getDocument (String url) throws Exception {
		//�õ�DocumentBuilderFactory
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		//�õ�DocumentBuilder
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		//ָ���ļ�
		return documentBuilder.parse(url);
	}
}

