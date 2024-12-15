package com.trace.agent.context;

import java.util.UUID;

public final class TraceContext {

    private TraceContext(){

    }

    private static final ThreadLocal<String> TRACE_CTX = new ThreadLocal<>();

    public static final String TRACE_ID="traceId";


    public static void createTraceId(){
        setTraceId(generateTraceId());
    }

    public static String getTraceId(){
        return TRACE_CTX.get();
    }

    public static void setTraceId(String traceId){
        TRACE_CTX.set(traceId);
    }

    public static void removeTraceId(){
        TRACE_CTX.remove();
    }

    public static String captureTraceId(){
        return getTraceId();
    }

    public static void continued(String traceId){
        setTraceId(traceId);
    }


    public static String generateTraceId(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
