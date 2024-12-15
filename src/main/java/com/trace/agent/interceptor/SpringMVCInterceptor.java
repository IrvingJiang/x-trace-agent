package com.trace.agent.interceptor;

import com.trace.agent.context.TraceContext;
import net.bytebuddy.asm.Advice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpringMVCInterceptor {

    @Advice.OnMethodEnter
    public static void intercept(@Advice.Argument(0) HttpServletRequest request,
                                 @Advice.Argument(1)  HttpServletResponse response) {
        System.out.println("doDispatch before");
       String traceId = request.getHeader(TraceContext.TRACE_ID);
       if(traceId==null || traceId.length()==0){
           TraceContext.createTraceId();
           return;
       }
       TraceContext.setTraceId(traceId);
    }

    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void afterIntercept(@Advice.Argument(0) HttpServletRequest request,
                                      @Advice.Argument(1) HttpServletResponse response) {
        System.out.println("doDispatch after");
        TraceContext.removeTraceId();
    }
}
