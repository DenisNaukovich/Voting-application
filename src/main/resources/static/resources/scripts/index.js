const ROOT_URL = "/voting/";
const PAGE_SIZE = 10;
var pageNumber = 0;

$(document).ready(function () {

    $("#add-voting-button").click(function(event){
        event.preventDefault();
        addVoting();
    });
    pageNumber = 0;
    getAll();

    $("#add-form").click(function() {
        $("#link").val("");
        $("#link").hide();
    });

});

function getAll() {
    $("#loadMore").prop("disabled", true);
    $.ajax({
        type: "GET",
        url: ROOT_URL + "page/" + pageNumber,
        dataType: 'json',
        success: function (data) {
            addVotingToPage(data);
            $("#loadMore").prop("disabled", false);
        },
        error: function (e) {
            alert('error');
        }
    });
}

function addVoting() {

    $("#add-voting-button").prop("disabled", true);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: ROOT_URL + "add",
        dataType: 'json',
        data: JSON.stringify(getVotingFromAddForm()),
        success: function (data) {
            if (!$("#loadMore").length)
                addVotingToPage([data]);
            setVotingLink(data.id);
            $("#add-voting-button").prop("disabled", false);
            alert("New voting was created");
        },
        error: function (e) {
            alert('error');
        }
    });
}

function getVotingFromAddForm() {
    var voting = {
        title: $("#title").val(),
        options: []
    };
    $.each($("#options").val().split("\n"), function(i, value){
        voting.options.push({name: value});
    });
    return voting;
}

function addVotingToPage(votingList) {
    if (votingList.length == 0) {
        removeLoadMoreButton()
        return;
    }

    var html = '';

    $.each(votingList, function (i, voting) {
        html += '<a href="'+ ROOT_URL+voting.id +'" class="list-group-item">'+ voting.title + ((voting.closed) ? '<span class="badge">closed</span></a>' : '</a>');
    });

    $(".list-group").append(html);

    if (!pageNumber && votingList.length == PAGE_SIZE) {
        addLoadMoreButton();
    }
    if (pageNumber && votingList.length < PAGE_SIZE) {
        removeLoadMoreButton();
    }
    pageNumber++;
}

function addLoadMoreButton() {
    $(".list-group").after('<button id="loadMore" class="btn btn-default">Load More</button>');
    $("#loadMore").click(function(){
        event.preventDefault();
        getAll();
    });
}

function removeLoadMoreButton() {
    if ($("#loadMore").length)
        $("#loadMore").remove();
}

function setVotingLink(id) {
    $("#link").val(window.location.href + "/" + id);
    $("#link").show();
}