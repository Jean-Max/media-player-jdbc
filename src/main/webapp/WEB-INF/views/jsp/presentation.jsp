<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style><%@include file="/WEB-INF/views/css/style.css"%></style>
<!DOCTYPE html>
<html>
    <body>

    <h1>${msg}</h1>

        <table id="musics">

            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>CATEGORY</th>
            </tr>

            <c:forEach items="${musics}" var="music" >
                <tr>
                    <td><c:out value="${music.id}"/></td>
                    <td><c:out value="${music.name}"/></td>
                    <td><c:out value="${music.category}"/></td>
                </tr>
            </c:forEach>

        </table>

    </body>
</html>

