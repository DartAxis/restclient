$(document).ready( function () {
    //getTable();


    let inputs = document.getElementsByTagName("input"), len = inputs.length, i, token;
    for (i = 0; i<len; i++) {
        if (inputs[i].name==="token") {
            token = inputs[i].value;
            break;
        }
    }

/*    $(document).ajaxSend(function (event, jqXHR, options) {
        if (options.crossDomain) {
            jqXHR.setRequestHeader("Authorization", token);

        }
    });*/
    //console.log(token);
    //modal form
    $(document).on('click', '#editUserBtn', function () {

        $("#updateUserId").val($(this).closest("tr").find("#tableId").text());
        $("#updateUserId").prop("disabled", true);

        $("#updateUserName").val($(this).closest("tr").find("#tableName").text());

        $("#updateUserEmail").val($(this).closest("tr").find("#tableAddress").text());

        $("#updateUserLogin").val($(this).closest("tr").find("#tableLogin").text());

        //$("#updateUserPass").hide();
        $("#updateUserPass").val($(this).closest("tr").find("#tablePass").text());
        $("#updateUserPass").prop("disabled", true);

        let role = $(this).closest("tr").find("#tableRole").text();
        let admin = "admin";
        if (role === admin) {
            $('#updateUserRole option:contains("admin")').prop("selected", true);
        } else {
            $('#updateUserRole option:contains("user")').prop("selected", true);
        }
    });

    //addForm
    $("#addFormUser").click(function (event) {
        event.preventDefault();
        addForm();
        $(':input', '#addForm').val('');
    });

    $("#resetTable").click(function () {
        getTable();
    });

    function addForm() {

        let user = {
            'name': $("#addName").val(),
            'login': $("#addLogin").val(),
            'password': $("#addPassword").val(),
            'role': $("#addRole").val(),
            'address': $("#addAddress").val()
        };

        $.ajax({

            type: 'POST',
            crossDomain:true,
            url: "http://localhost:8383/restapi/users/",

            contentType: 'application/json;',
            data: JSON.stringify(user),
            headers: {
                'Authorization':token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            dataType: 'JSON',
        });
    }

    //updateForm
    $("#updateFormUser").click(function (event) {
        event.preventDefault();
        updateForm();
        $("#editUser").modal('toggle');
        getTable();
    });

    function updateForm() {
        let user = {
            'id': $("#updateUserId").val(),
            'name': $("#updateUserName").val(),
            'address': $("#updateUserEmail").val(),
            'login': $("#updateUserLogin").val(),
            'password': $("#updateUserPass").val(),
            'role': $("#updateUserRole").val()
        };

        $.ajax({

            crossDomain:true,
            type: 'Put',
            url: "http://localhost:8383/restapi/users/",

            contentType: 'application/json;',
            data: JSON.stringify(user),
            headers: {
                'Authorization': token,
            },
            success: function () {
                getTable();
            }
        });
    }

    //deleteForm
    $("#deleteUser").click(function (event) {
        event.preventDefault();
        deleteUser();
        getTable();
    });

    function deleteUser() {
        let id = $("#updateUserId").val();

        $.ajax({
            type: 'DELETE',
            crossDomain:true,
            url: "http://localhost:8383/restapi/users/",
            contentType: 'application/json;',
            data: JSON.stringify(id),
            headers: {
                'Authorization': token,
            },
            success: function () {
                getTable();
            }
        });
    }

    $(document).ready( function () {
        getTable();
    });

    function getTable() {
        // console.log(token);
        $.ajax({
            type: 'GET',
            crossDomain:true,
            url: "http://localhost:8383/restapi/users/",
            contentType: 'application/json;',
            headers: {
                'Authorization':token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType: 'JSON',
            success: function (listUsers) {
                let htmlTable = "";
                for (let i = 0; i < listUsers.length; i++) {
                    let htmlRole = listUsers[i].roles[0].role;

                    htmlTable += `<tr id="list">
                        <td id="tableId" class="text-center">${listUsers[i].id}</td>
                        <td id="tableRole"> ${htmlRole} </td>
                        <td id="tableLogin"> ${listUsers[i].login} </td>
                        <td hidden id="tableName"> ${listUsers[i].name}  </td>
                        <td id="tablePass">  ${listUsers[i].password}  </td>
                        <td id="tableAddress">  ${listUsers[i].address}  </td>
                        <td><button id="editUserBtn"  class="btn btn-primary" type="button" data-toggle="modal" data-target="#editUser">Edit</button></td>
                        </tr><br>`;
                }
                $("#tableUser #list").remove();
                $("#tableUser thead").after(htmlTable);
            }
        })}
});

