package com.trace.agent;
import com.trace.agent.interceptor.SpringMVCInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.instrument.Instrumentation;


public class TraceAgent {
    public static void premain(String arguments, Instrumentation instrumentation) {
        //应用程序启动时进行一些初始化操作，比如设置字节码转换器（如使用Byte Buddy进行字节码增强）
        (new AgentBuilder.Default()).type(ElementMatchers.nameContains("DispatcherServlet")).transform((builder, typeDescription, classLoader, module) -> {
            return builder.method(ElementMatchers.named("doDispatch")
                            .and(ElementMatchers.isProtected())
                            .and(ElementMatchers.takesArguments(new Class[]{HttpServletRequest.class, HttpServletResponse.class})))
                    .intercept(Advice.to(SpringMVCInterceptor.class));
        }).installOn(instrumentation);
    }

}
