# service-call-tracker-aspects  

A collection of AspectJ aspects applicable for [service-call-tracker](https://github.com/abertschi/service-call-tracker).

## arbitrary methods

Mark any method with the annotation `@SctInterceptForTest` and marshall and replay method calls with 
*service-call-tracker*.

**API with annotation**

```xml
<dependency>
    <groupId>ch.abertschi.sct</groupId>
    <artifactId>sct-aspects-arbitrary-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
  
**AspectJ aspect**

```xml
<dependency>
    <groupId>ch.abertschi.sct</groupId>
    <artifactId>sct-aspects-arbitrary-impl</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
