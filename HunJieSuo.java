/**
 * ���׽ӿ�
 * 
 * @author zhengt
 * @time Jun 3, 2095 3:13:03 PM
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface XiangQinInterface {
	/**
	 * ���׷���
	 */
	public void xiangQin();
}
/**
 * ��������ʵ����
 * 
 * @author zhengt
 * @time Jun 3, 2095 3:14:48 PM
 */
class ZhangSanXiangQinInterfaceImpl implements XiangQinInterface {
	public void xiangQin() {
		System.out.println("����ȥ���ף�Ȣ��Ư�����š�");
	}
}


/**
 * ���׿���һ���ӵĴ��£�����ǰҪ׼��һ�£�����˧��Щ��
 * 
 * @author zhengt
 * @time Jun 3, 2095 3:15:48 PM
 */
class ReadyInvocationHandler implements InvocationHandler {
	//���׽ӿڵ�ʵ���࣬Ҳ��������������
	private Object zhangSan = null;

	public ReadyInvocationHandler(Object realSubject) {
		this.zhangSan = realSubject;
	}

	public Object invoke(Object proxy, Method m, Object[] args) {
		Object result = null;
		try {
			/**
			 * ��̬������$Proxy0����xiangQin����ʱ��������Լ���xiangQin������
			 * �����Լ���xiangQin����������õ���super.h.invoke(this, , )��Ҳ���Ǹ���Proxy��h��invoke������
			 * Ҳ����ReadyInvocationHandler���invoke������
			 * ���ԣ�invoke(Object proxy, Method m, Object[] args)�ֵ�proxyʵ���Ͼ��Ƕ�̬������$Proxy0��
			 * ����㽫��ǿת��XiangQinInterfaceȻ���������xiangQin������Ȼ�����ͻ����super.h.invoke(this, , )�������ͻ���ѭ����
			 */
			/**
			 * ���Ϲ�����������������Object proxy��������������ʲô�أ������Ҳ��֪����
			 * ������������֪���������Ǹ�ʲô������������ʲô����Ͳ��ö�֪��
			 */
			System.out.println(proxy.getClass().getSimpleName());
			System.out.println("��������ǰ�������˸�������˴�硣");
			result = m.invoke(zhangSan, args);
		} catch (Exception ex) {
			System.exit(1);
		}
		return result;
	}
}


/**
 * ���������˻����(�����ֳ�)����ʼ���ס�
 * 
 * @author zhengt
 * @time Jun 3, 2095 3:17:16 PM
 */
public class HunJieSuo {
	public static void main(String args[]) {
		//�Ƚ���������������׵�ʵ����ʵ������Ҳ���ǵõ�XiangQinInterface�ӿڵ�һ��ʵ������
		XiangQinInterface zhangSan = new ZhangSanXiangQinInterfaceImpl();
		/**
		 * �õ�ZhangSanXiangQinInterfaceImpl������һ�������࣬ͬʱΪ���������һ��������ReadyInvocationHandler��
		 * ���ź��ƿڣ���ʵ����ÿ�ε���ZhangSanXiangQinInterfaceImpl��������xiangQin����ʱ��
		 * ����zhangSan���ZhangSanXiangQinInterfaceImpl���ʵ��ȥ���ã�
		 * �������ZhangSanXiangQinInterfaceImpl�Ĵ�����ReadyInvocationHandlerȥ�������Լ���invoke����,
		 * ���invoke�������ؿ��Ե���zhangSan���ʵ����xiangQin����
		 */
		/**
		 * ��java������ʵ�ֶ�̬������
		 * ��һ��������Ҫ��һ���ӿڣ���Ҫ��һ���ӿڵ�ʵ���࣬�����ʵ�����ؾ�������Ҫ����Ķ���
		 * ��ν������Ҳ�����ڵ���ʵ����ķ���ʱ�������ڷ���ִ��ǰ��������Ĺ�����������Ǵ���
		 * �ڶ���������Ҫ�Լ�дһ����Ҫ������ķ���ִ��ʱ���ܹ������⹤�����࣬����������̳�InvocationHandler�ӿڣ�
		 * ΪʲôҪ�̳����أ���Ϊ�������ʵ���ڵ���ʵ����ķ�����ʱ�򣬲����������ʵ��������������
		 * ����ת������������invoke�������̳�ʱ����ʵ�ֵķ����������������������Ե���������ʵ��������������
		 * ����������Ҫ�ô������ʵ��ȥ����ʵ����ķ�����ʱ��д���������δ��롣
		 */
		XiangQinInterface proxy = (XiangQinInterface) Proxy.newProxyInstance(
				zhangSan.getClass().getClassLoader(),
				zhangSan.getClass().getInterfaces(),
				new ReadyInvocationHandler(zhangSan));
		proxy.xiangQin();
		/**
		 * ����Ҫ�������в��Ƕγ����Ĵ������˼���Լ�����������Щ������
		 * ��һ������zhangSan.getClass().getClassLoader()���Ҫ����������������
		 * zhangSan.getClass().getInterfaces()Ҫ��������ʵ�ֵ����еĽӿ�
		 * ��Ϊ��������Proxy.getProxyClass(ClassLoader loader, Class<?>... interfaces)
		 * �ķ������ش������java.lang.Class����Ҳ���ǵõ���java��̬���ɵĴ�����$Proxy0��Class����
		 * ͬʱ��java���������̬���ɵ�$Proxy0��ʵ����Ҫ�������ʵ�ֵ����нӿڣ����̳���Proxy�ӿڡ�
		 * �ڶ���ʵ���������̬���ɵ�$Proxy0���һ��ʵ����ʵ����������Ĺ��캯��ΪProxy(InvocationHandler h)��
		 * Ҳ����˵Ҫʵ���������̬���ɵ�$Proxy0�࣬�������һ��InvocationHandler������Ҳ���������Լ�ʵ�ֵ������ڴ�����
		 * ����ִ��ǰ�������⹤������ReadyInvocationHandler��
		 * ��δ���Proxy.newProxyInstance(zhangSan.getClass().getClassLoader(),zhangSan.getClass().getInterfaces(),new ReadyInvocationHandler(zhangSan))
		 * �õ�����ʵ��һ��������$Proxy0 extends Proxy implements XiangQinInterface���ࡣ
		 * �����������$Proxy0��ǿ��ת�ͳ�XiangQinInterface���ͣ�����xiangQin������
		 */
	}
}
