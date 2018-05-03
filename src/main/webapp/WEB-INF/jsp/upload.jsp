<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>

<body>
<h1>Spring MVC multi files upload example</h1>

<form:form method="POST" action="http://localhost:8080/uploadFile" enctype="multipart/form-data">

    <input type="file" name="file" /><br/>
    <input type="submit" value="Submit" />

</form:form>

<form:form method="POST" action="http://localhost:8080/run" enctype="multipart/form-data">
    <input type="submit" value="RUN" />
</form:form>
<h2>Number of record today :${count}</h2>
<br>
<h2>Number rsi errors :${countrsi}</h2>

<h3>Import :${importstatus}</h3>
<h3>Average :${averagestatus}</h3>
<h3>RSI: ${rsistatus}</h3>
<h3>Algo :${algostatus}</h3>




<a href="/run">Run all import </a>
<br>

<a href="/calc">Calc all data  </a>
<br>

<a href="/algo">Run Algo </a>

</body>

</html>