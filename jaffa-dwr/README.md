# jaffa-dwr Public Methods

***
**org.jaffa.dwr.DwrServletTest**

```
public InputStream getResourceAsStream(String path)
public void testDwrLoad() throws Exception
```
***
**org.jaffa.dwr.FakeServletContext**

```
public InputStream getResourceAsStream(String path)
```
***
**org.jaffa.dwr.converters.BooleanConverter**

```
public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
```
***
**org.jaffa.dwr.converters.CurrencyConverter**

```
public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException
```
***
**org.jaffa.dwr.converters.DateConverter**

```
public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException
```
***
**org.jaffa.dwr.converters.FlexBeanConverter**

```
public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException
```
***
**org.jaffa.dwr.converters.FlexCriteriaBeanConverter**

```
public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException
```
***
**org.jaffa.dwr.converters.FlexDescriptor**

```
public String getName()
public Class getPropertyType()
public Method getSetter()
public Object getValue(Object bean) throws MarshallException
public void setValue(Object bean, Object value) throws MarshallException
```
***
**org.jaffa.dwr.converters.JaffaDateConverter**

```
public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException
```
***
**org.jaffa.dwr.converters.NotNullBeanConverter**

```
public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException
```
***
**org.jaffa.dwr.services.ConfigurationService**

```
public List<Create> getCreateList()
public static ConfigurationService getInstance()
```
***
**org.jaffa.dwr.services.configdomain.Allow**

```
public List<Object> getCreateOrConvertOrFilter()
```
***
**org.jaffa.dwr.services.configdomain.Auth**

```
public String getMethod()
public String getRole()
public void setMethod(String value)
public void setRole(String value)
```
***
**org.jaffa.dwr.services.configdomain.Convert**

```
public String getConverter()
public String getJavascript()
public String getMatch()
public List<Param> getParam()
public void setConverter(String value)
public void setJavascript(String value)
public void setMatch(String value)
```
***
**org.jaffa.dwr.services.configdomain.Converter**

```
public String getClazz()
public String getId()
public void setClazz(String value)
public void setId(String value)
```
***
**org.jaffa.dwr.services.configdomain.Create**

```
public String getCreator()
public String getJavascript()
public List<Object> getParamOrIncludeOrExclude()
public String getScope()
public void setCreator(String value)
public void setJavascript(String value)
public void setScope(String value)
```
***
**org.jaffa.dwr.services.configdomain.Creator**

```
public String getClazz()
public String getId()
public void setClazz(String value)
public void setId(String value)
```
***
**org.jaffa.dwr.services.configdomain.Dwr**

```
public Allow getAllow()
public Init getInit()
public String getSignatures()
public void setAllow(Allow value)
public void setInit(Init value)
public void setSignatures(String value)
```
***
**org.jaffa.dwr.services.configdomain.Exclude**

```
public String getMethod()
public void setMethod(String value)
```
***
**org.jaffa.dwr.services.configdomain.Filter**

```
public String getClazz()
public List<Param> getParam()
public void setClazz(String value)
```
***
**org.jaffa.dwr.services.configdomain.Include**

```
public String getMethod()
public void setMethod(String value)
```
***
**org.jaffa.dwr.services.configdomain.Init**

```
public List<Object> getCreatorOrConverter()
```
***
**org.jaffa.dwr.services.configdomain.ObjectFactory**

```
public Allow createAllow()
public JAXBElement<Allow> createAllow(Allow value)
public Auth createAuth()
public JAXBElement<Auth> createAuth(Auth value)
public Convert createConvert()
public JAXBElement<Convert> createConvert(Convert value)
public Converter createConverter()
public JAXBElement<Converter> createConverter(Converter value)
public Create createCreate()
public JAXBElement<Create> createCreate(Create value)
public Creator createCreator()
public JAXBElement<Creator> createCreator(Creator value)
public Dwr createDwr()
public JAXBElement<Dwr> createDwr(Dwr value)
public Exclude createExclude()
public JAXBElement<Exclude> createExclude(Exclude value)
public Filter createFilter()
public JAXBElement<Filter> createFilter(Filter value)
public Include createInclude()
public JAXBElement<Include> createInclude(Include value)
public Init createInit()
public JAXBElement<Init> createInit(Init value)
public Param createParam()
public JAXBElement<Param> createParam(Param value)
public JAXBElement<String> createSignatures(String value)
```
***
**org.jaffa.dwr.services.configdomain.Param**

```
public String getName()
public String getValue()
public void setName(String value)
public void setValue(String value)
```
***
**org.jaffa.dwr.servlet.DwrServlet**

```
public void init(ServletConfig servletConfig) throws javax.servlet.ServletException
```
***
**org.jaffa.dwr.util.ContainerUtil**

```
public static void configureFromResource(Container container, ServletConfig servletConfig, Resource[] resources) throws IOException, ParserConfigurationException, SAXException
```
***
**org.jaffa.dwr.util.DwrXmlConfigurator**

```
public void setServletResourceName(Resource resource) throws IOException, ParserConfigurationException, SAXException
```
