package com.telran.json;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * This is simple JSON Parser, providing recursive writing and parsing of JSONs representing objects and arrays.
 * The supposed format of all values are quoted strings, inheritance and generic types are not covered.
 * @author Daniel Z.
 */
public class Json{
	private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	private final static Map<Class<?>, Function<String,?>> parsersByType = new IdentityHashMap<>();
	
	Map<Class<?>, HashMap<String, Field>> typeModels = new IdentityHashMap<>();
	
	static {
        parsersByType.put(byte.class, Byte::parseByte);
        parsersByType.put(Byte.class, Byte::parseByte);
        parsersByType.put(short.class, Short::parseShort);
        parsersByType.put(Short.class, Short::parseShort);
        parsersByType.put(int.class, Integer::parseInt);
        parsersByType.put(Integer.class, Integer::parseInt);
        parsersByType.put(long.class, Long::parseLong);
        parsersByType.put(Long.class, Long::parseLong);

        parsersByType.put(float.class, Float::parseFloat);
        parsersByType.put(Float.class,  Float::parseFloat);
        parsersByType.put(double.class, Double::parseDouble);
        parsersByType.put(Double.class, Double::parseDouble);
        
        parsersByType.put(boolean.class, Boolean::parseBoolean);
        parsersByType.put(Boolean.class,  Boolean::parseBoolean);
        
		parsersByType.put(String.class, Function.identity());
		
        parsersByType.put(LocalDate.class, v -> LocalDate.parse(v, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
	}
	
	public <T> T parse(String json, Class<T> type) throws Exception{
		SimpleJsonStringTokenizer  tokenizer = new SimpleJsonStringTokenizer(json);
		T res =  parseValue(tokenizer, type);
		if (tokenizer.hasNext()) {
			throw new RuntimeException("Json has excessive fields");
		}
		return res;
	}
	
	//The method should parse json-string from Reader jsonStream
	public <T> T parse(Reader reader, Class<T> type) throws Exception{
		try(
				SimpleJsonFileTokenizer  tokenizer = new SimpleJsonFileTokenizer(reader);
			){
			T res =  parseValue(tokenizer, type);
			if (tokenizer.hasNext()) {
				throw new RuntimeException("Json has excessive fields");
			}
			return res;
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T parseValue(SimpleJsonTokenizer tokenizer, Class<T> type) throws Exception{
		Function<String,?> parser = parsersByType.get(type);
		if (parser != null) {
			return (T)parser.apply(stripQuotes(tokenizer.next()));
		}
		if(type.isArray()) {
			return parseArray(tokenizer, type);
		}
		return parseObject(tokenizer, type);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T parseValue(SimpleJsonTokenizer tokenizer, Class<T> type, String format) throws Exception {
		if (format != null) {
			if (type == LocalDate.class) {
				return (T) LocalDate.parse(stripQuotes(tokenizer.next()), DateTimeFormatter.ofPattern(format));
			}
//			throw new RuntimeException("Unsupported format '" + format + "' for type " + type);
		}
		return parseValue(tokenizer, type);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T parseArray(SimpleJsonTokenizer tokenizer, Class<T> arrType) throws Exception {
		List<Object> list = new ArrayList<>();
		Class<?> eltType = arrType.getComponentType();
		assertNextToken(tokenizer, "[");
		while(!tokenizer.peek().equals("]")) {		
            if (list.size()>0) {					// non-first element
            	assertNextToken(tokenizer, ",");
            }
			Object elt = parseValue(tokenizer, eltType);
			list.add(elt);
		}
		tokenizer.next(); // extract ]	
		int eltCount = list.size();
		Object actualArray = Array.newInstance(eltType, eltCount); // creation of generic array using reflection
		return (T) list.toArray((Object[])actualArray);
	}
	
	private <T> T parseObject(SimpleJsonTokenizer tokenizer, Class<T> objType) throws Exception{
		T res = objType.getDeclaredConstructor().newInstance();
		assertNextToken(tokenizer, "{");
		int parsedFields = 0;
		while(!tokenizer.peek().equals("}")) {
			if (parsedFields > 0) {
				assertNextToken(tokenizer, ",");
			}
			String key = tokenizer.next();			
			//Field field = objType.getDeclaredField(stripQuotes(key));
			Field field = getObjectFieldByJsonKey(objType,stripQuotes(key)); // annotation support
            assertNextToken(tokenizer, ":");
            //Object value = parseValue(tokenizer, field.getType());
            Object value = parseValue(tokenizer, field.getType(), getFieldValueFormat(field)); // annotation support
            field.setAccessible(true);
            field.set(res, value);
            parsedFields++;
		}
		tokenizer.next();  // extract }	
		return res;
	}
		
	private String getFieldValueFormat(Field fld) {		
		return (fld.isAnnotationPresent(JsonFormat.class)) ? fld.getAnnotation(JsonFormat.class).value() : null;
	}

	private Field getObjectFieldByJsonKey(Class<?> objType, String key) {
		Field fld =  typeModels.computeIfAbsent(objType, Json::createTypeModel).get(key);
		if (fld == null) {
			throw new RuntimeException("Unknown field '" + key + "' for type " + objType);
		}
		return fld;
	}

	private static HashMap<String, Field> createTypeModel(Class<?> objType) {
		HashMap<String, Field> model = new HashMap<>();
		for (Field fld: objType.getDeclaredFields()) {
			String name = (fld.isAnnotationPresent(JsonField.class)) ? fld.getAnnotation(JsonField.class).value() :fld.getName();
			model.put(name, fld);
		}
		return model;
	}
	
	private void assertNextToken(SimpleJsonTokenizer tokenizer, String expectedToken) {
		String nextToken = tokenizer.next();
		if (! nextToken.equals(expectedToken)) {
			throw new RuntimeException("Unexpected token " + nextToken + " instead of " + expectedToken);
		}
	}
	
	private String stripQuotes(String quotedString) {
		if (!quotedString.matches("\".*\"")) {
			throw new RuntimeException("Invalid token instead of quoted string: " + quotedString);
		}
		return quotedString.substring(1, quotedString.length()-1);
	}
	
	// ============================= Writer Json ======================================
	
	//Convert object (T obj) to json-string and return it;
	public  String writeAsString(Object obj) throws Exception{
		StringBuilder sb = new StringBuilder();
		writeValue(obj, s->sb.append(s), null);
		return sb.toString();
	}

	//The method should convert object (T obj) to json-string and write it to Writer os
	public void writeToStream(Writer os, Object obj) throws Exception {
		try(os){
			writeValue(obj, t -> {
				try {
					os.write(t);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}, null);
		} 
	}
	
	private void writeValue(Object obj, Consumer<String> outputConsumer, String format) throws Exception{
		Class<?> type = obj.getClass();
		if (parsersByType.containsKey(type)) { // is "primitive type" or LocalDate
			outputConsumer.accept("\"");
			outputConsumer.accept(formatValue(obj, format));
			outputConsumer.accept("\"");
			return;
		}
		if (type.isArray()) {
			writeAsArray(obj, outputConsumer);
			return;
		}
		writeAsObject(obj, outputConsumer);
	}

	private void writeAsArray(Object arrObj, Consumer<String> outputConsumer) throws Exception {
		int length = Array.getLength(arrObj);
		outputConsumer.accept("[");
		for (int index = 0 ; index < length; index++) {
			if (index > 0) {
				outputConsumer.accept(",");
			}
			writeValue(Array.get(arrObj, index), outputConsumer, null);
		}
		outputConsumer.accept("]");
	}
	
	private void writeAsObject(Object obj, Consumer<String> outputConsumer) throws Exception {
		outputConsumer.accept("{");
		int writtenFields = 0;
        for (Field field : obj.getClass().getDeclaredFields()) {
        	if (writtenFields++ > 0) {
				outputConsumer.accept(",");
			}
            String key = field.isAnnotationPresent(JsonField.class) ? field.getAnnotation(JsonField.class).value() : field.getName();
			outputConsumer.accept("\"");
			outputConsumer.accept(key);
			outputConsumer.accept("\"");
            outputConsumer.accept(":");
            String format = field.isAnnotationPresent(JsonFormat.class) ? field.getAnnotation(JsonFormat.class).value() : null;
            field.setAccessible(true);
            writeValue(field.get(obj), outputConsumer, format);
        }
        outputConsumer.accept("}");
		
	}

	private <T> String formatValue(T obj, String format) {
		if (obj.getClass() == LocalDate.class) {
			format = (format == null) ? DEFAULT_DATE_FORMAT : format;
			return ((LocalDate)obj).format(DateTimeFormatter.ofPattern(format));
		}
		if (format == null) {
			return obj.toString();	
		}		
		//throw new RuntimeException("Unsupported format '" + format + "' for type " + obj.getClass());
		return String.format(format, obj);
	}
}

