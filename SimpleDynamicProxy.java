import java.lang.reflect.*;
class DynamicProxyHandler implements InvocationHandler
{
	private Object proxied;
	public DynamicProxyHandler(Object proxied){
		this.proxied=proxied;
	}
	public Object invoke(Object proxy,Method method,Object[] args) throws Throwable{
		System.out.println("****** proxy: " + proxy + ", method: " + method + ", args: " + args);
		if(args!=null){
			for(Object arg:args)
				System.out.print(" "+arg);
		}
		return method.invoke(proxied,args);
	}
}
class  SimpleDynamicProxy
{
	public static void consumer(Interface iface){
		iface.doSomething();
		iface.somethingElse("bonobo");
	}
	public static void main(String[] args) 
	{
		RealObject real=new RealObject();
		consumer(real);
		System.out.println("Hello ================================= World!");
		
		Interface proxy=(Interface)Proxy.newProxyInstance(
		Interface.class.getClassLoader(),
			new Class[]{Interface.class},
			new DynamicProxyHandler(real));
		System.out.println(proxy.toString());
		//consumer(proxy);
		
	}
}
