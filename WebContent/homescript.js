var Username = 'defaultUser'
Username = sessionStorage.getItem("Username");
if (Username == undefined) {
    //location.href = 'index.html';
}
function addmailListeners(mailrendertype) {
    let lists = document.getElementById(mailrendertype).querySelectorAll('li');
    lists.forEach(list => {

        let st = list.querySelector('.starIcon');
        console.log(st);
        st.addEventListener('click', (e) => {
            if (st.innerHTML == 'star') {
                st.innerHTML = 'star_border'
            } else {
                st.innerHTML = 'star'
            }
            let form = new FormData();
            console.log(e.target.dataset.mailid);
            form.set("mailID", e.target.dataset.mailid);
            console.log(JSON.stringify(form));
            let response = fetch('starMail', {
                method: 'POST',
                body: form,
            }).then((data) => data.json())
                .then(data => data)
                .catch((err) => {
                    console.log(err)
                })

        });
        var servletCall = 'binMail';
        if (mailrendertype == 'trashmailList') servletCall = 'DeleteMails'
        let del = list.querySelector('.delteIcon');
        del.addEventListener('click', (e) => {
            let form = new FormData();
            console.log(e.target.dataset.mailid);
            form.set("mailID", e.target.dataset.mailid);
            console.log(JSON.stringify(form));
            let response = fetch(servletCall, {
                method: 'POST',
                body: form,
            }).then((data) => data.json())
                .then(data => data)
                .catch((err) => {
                    console.log(err)
                })
            document.getElementById(e.target.dataset.mailid).remove();
        })
        let card = list.querySelector('.truncate');
        card.addEventListener('click', (e) => {
            console.log(Username, e.target.dataset.mailid);
            fetchmailcontent(e.target.dataset.mailid);
            document.getElementById('mailview').style.display = "block";
            disableAll();
        })
    });
}
/**
 * function name addcomposelisteners 
 * adds listeners to the compose div popup
 */
function addComposelisterners() {
    console.log('Fetching composeWrap')
    let lists = document.getElementById('cmpBX').querySelectorAll('li');
    console.log(lists)
    lists.forEach(list => {
        list.querySelector('.closeCompose').onclick = function (e) {
            let compose = list;
            console.log(compose.id);
            compose.remove();
        }
        list.querySelector('#sendBtn').onclick = (e) => {
            e.preventDefault();
            let formdat = new FormData(f1);
            let tomail = document.getElementById("To").value;
            let mailsub = document.getElementById("Subject").value;
            let mailcnt = document.getElementById("mailContent").value;
            formdat.set("mailtype", "inbox");
            formdat.set("frommail", sessionStorage.getItem("Username"));
            formdat.set("tomail", tomail);
            formdat.set("mailsub", mailsub);
            formdat.set("mailcnt", mailcnt);
            sendmail(formdat);
            list.remove()
        }
        list.querySelector('#draftBtn').onclick = (e) => {
            let formdat = new FormData(f1);
            let tomail = document.getElementById("To").value;
            let mailsub = document.getElementById("Subject").value;
            let mailcnt = document.getElementById("mailContent").value;
            formdat.set("mailtype", "draft");
            formdat.set("frommail", sessionStorage.getItem("Username"));
            formdat.set("tomail", tomail);
            formdat.set("mailsub", mailsub);
            formdat.set("mailcnt", mailcnt);
            draftmail(formdat);
            list.remove()
        }
    });
}

const disableAll = () => {
    let containers = ["inboxmailList", "draftmailList", "sentmailList", "starredmailList", "trashmailList"];
    let activators = ["inbox", "draft", "sent", "starred", "trash"]
    let map = new Map();
    for (let key in containers) {
        map.set(containers[key], activators[key])
    }
    for (let container of containers) {
        document.getElementById(container).style.display = "none";
        document.getElementById(map.get(container)).style.background = "transparent";
    }
}

const makeVisible = (containerID) => {
    let containers = ["inboxmailList", "draftmailList", "sentmailList", "starredmailList", "trashmailList"];
    let activators = ["inbox", "draft", "sent", "starred", "trash"]
    let map = new Map();
    for (let key in containers) {
        map.set(containers[key], activators[key])
    }
    for (let container of containers) {
        if (container == containerID) {
            console.log(container)
            document.getElementById(container).style.display = "block";
            document.getElementById(map.get(container)).style.background = "rgb(66, 66, 65)";
            continue;
        }
        document.getElementById(container).style.display = "none";
        document.getElementById(map.get(container)).style.background = "transparent";
    }
    document.getElementById("mailview").style.display = "none";
}

/**
 * 
 * @param {string} emailID user mailID
 * @param {string} type type of the mail to be retrieved
 */

var fetchMail = async (emailID, type) => {
    makeVisible(`${type}mailList`);
    let form = new FormData();
    form.set("emailID", emailID);
    form.set("mailtype", type);
    console.log(form);
    let response = await fetch('FetchMails', {
        method: 'POST',
        body: form,
    }).then((data) => data.json())
        .then(data => data)
        .catch((err) => {
            console.log(err)
        })
    console.log(response)
    renderMail(`${type}mailList`, response);
}

document.querySelector('.logoutBox').addEventListener('click', () => {
    sessionStorage.clear();
    location.href = 'index.html'
});

inbox.onclick = () => {
    fetchMail(Username, "inbox");
}
draft.onclick = () => {
    fetchMail(Username, "draft");
}
sent.onclick = () => {
    fetchMail(Username, "sent");
}
starred.onclick = () => {
    fetchMail(Username, "starred");
}
trash.onclick = () => {
    fetchMail(Username, "trash");
}


function renderMail(mailrendertype, maildata) {
    console.log(mailrendertype)
    console.log(maildata.length)
    document.getElementById(mailrendertype).innerHTML = "";
    if (maildata.length == 0) {
        document.getElementById(mailrendertype).innerHTML = `
                <h5 style="margin: 20% 40%; color:aliceblue;" >
                    No messages found
                </h5>`;
        return;
    }
        var buttonHolder = 'delete';
        var starHolder = 'star_border';
        if (mailrendertype == 'trashmailList') starHolder = 'restore';
        if (mailrendertype == 'starredmailList') starHolder = 'star';
            for (let i = 0; i < maildata.length; i++) {
                var mailStr = `<li>
                              <div class="myCard" id=${maildata[i].mailid} data-mailid=${maildata[i].mailid}>
                                    <img data-mailid=${maildata[i].mailid} src="https://img.icons8.com/material-rounded/24/000000/important-mail.png"
                                          style="margin: 0 10px;" />
                                    <div class="text truncate" data-mailid=${maildata[i].mailid} >
                                    <b data-mailid=${maildata[i].mailid}> ${maildata[i].mailcontent['sname']}</b>&nbsp<strong data-mailid=${maildata[i].mailid}>${maildata[i].mailcontent['subject']}</strong>
                                     &nbsp${maildata[i].mailcontent['message']}
                                    </div>
                                    <div id="mailTime" style="margin-left:auto" data-mailid=${maildata[i].mailid}>
                                          <p style="display: block;width: 100px;padding-right: 5px;right: 5px;"> 
                                                <strong>${maildata[i].maildate} </strong>
                                          </p>
                                    </div>
                                    <div id="mailOptions" data-mailid=${maildata[i].mailid}>
                                          <i class="small material-icons starIcon" id="star_inbox_read" data-mailid=${maildata[i].mailid}
                                                style="margin: 0 10px;">${starHolder}</i>
                                          <i class="small material-icons delteIcon" id="delete_inbox_read" data-mailid=${maildata[i].mailid}
                                                style="margin: 0 10px;">${buttonHolder}</i>
                                    </div>
                              </div>
                        </li>`;
                document.getElementById(mailrendertype).innerHTML += mailStr;
            }
        addmailListeners(mailrendertype);
    }
    //TODO fetchMail(Username, "inbox");

    var homeContents;
    var flag = true;
    var composeBoxElement = `
    <div class="composeWrap">
        <div class="headerBox">
            <p style="margin-left:3%;transform: translateY(-10px);" color:white>New Message<p>
            <div class="closeCompose">X</div>
        </div>
        <div style="height:450px;width:500px">
            <div class="head_area">
                <div class="row" style="margin-bottom: 0px;">
                    <form id="f1" >
                        <input type="text" placeholder="Recepients:" id="To" name="To" required />
                        <input type="text" id="Subject" placeholder="Subject" name="Subject" />
                        <textarea placeholder="Content Here!" id="mailContent" class="txtarea" spellcheck="true"></textarea>
                        <br>
                        <label for="myfile">Select files:</label>
                        <input type="file" id="myfile" name="myfile" multiple style="float: left;">
                    </form>
                    <button id="sendBtn" value="Send" >
                        <i class="material-icons">send</i>
                    </button>
                    <button id="draftBtn" value="Draft" >
                        <i class="material-icons">drafts</i>
                    </button>                       
                    <div class="col l3 m4 s12">
                        <div class="row extraIcons"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>`;

    /**
     * creates a new compose element on the page
     */
    function addComposeBox() {
        var cmpBox = document.getElementById("cmpBX");
        var composeBox = document.createElement('li');
        composeBox.innerHTML = composeBoxElement;
        cmpBox.append(composeBox);
        addComposelisterners();
    }
    /**
     * initiates an SMPT request to the server to send an Email
     * @param {*} formdat 
     */
    const sendmail = async (formdat) => {

        console.log(JSON.stringify(formdat));
        let data = await fetch('SMTP', {
            method: 'POST',
            body: formdat,
        }).then((data) => data.json())
            .then(data => data)
            .catch((err) => {
                console.log(err)
            })
        console.log(data);
        return data;
}
    
const draftmail = async (formdat) => {

    console.log(JSON.stringify(formdat));
    let data = await fetch('DraftMail', {
        method: 'POST',
        body: formdat,
    }).then((data) => data.json())
        .then(data => data)
        .catch((err) => {
            console.log(err)
        })
    console.log(data);
    return data;
}

    fetchMail(Username, "inbox");

    var mailviewcontent = `<div class="head_area" style="display: flex;justify-content: space-between; ">
                              <div style="display: flex;">
                                    <div style="display: block; margin: 8% 0 0 13%;">
                                          <img src="./Resources/profileSample.png" class="profileImage" alt="profile image"
                                                style="height: 65px;width: 65px; border-radius: 50%;">
                                    </div>
                                    <div style="display: block; margin: 5% 0% 0 5%;">
                                          <p id="mailUname" style="color: #fff;width: auto;"></p>
                                          <p id="mailUID" style="color: #fff ;margin-top: -2%;width: 300px;"></p>
                                    </div>
                              </div>
                              <div class="extraIcons">
                                    <div>
                                          <i class="small material-icons inboxIcons starIcon" style="color: black;"
                                                id="star_inbox_read">star_border</i>
                                    </div>
                                    <div>
                                          <i class="small material-icons inboxIcons" style="color: black;" id="delete_inbox_read">delete</i>
                                    </div>
                                    <div id="dropdowndiv">
                                          <i class="small material-icons inboxIcons" style="color: black;" id="more_inbox_options">more_vert</i>
                                          <ul id='dropdown1'>
                                                <li><a href="#!"><i class="material-icons dropicons">reply</i>reply</a>
                                                </li>
                                                <li><a href="#!"><i class="material-icons dropicons">forward</i>forward</a>
                                                </li>
                                                <li><a href="#!"><i class="material-icons dropicons">print</i>print</a>
                                                </li>
                                          </ul>
                                    </div>
                              </div>
                        </div>
                        <div class="content_area">
                              <div id="mailBody" style="background-color: #fff;padding: 1%;">
                                    <span id="mailSubject" style="font-weight: bold;color: black; font-size: large;"></span>
                                    <p id="mailText" style="min-height: 300px;background-color: #fff; width: 100%;margin: 0 2% 0 1%;">
                        
                                    </p>
                              </div>
                        </div>`;
    var fetchmailcontent = async (mailID) => {
        let form = new FormData();
        form.set("mailID", mailID);
        console.log(mailID + '---------------->')
        console.log(JSON.stringify(form));
        let response = await fetch('FetchMailcontent', {
            method: 'POST',
            body: form,
        }).then((data) => data.json())
            .then(data => data)
            .catch((err) => {
                console.log(err)
            })
        console.log(response)
        rendermailcontent(response);
    }

    function rendermailcontent(mailCnt) {
        let name = mailCnt['mailcontent'].sname;
        let mail = mailCnt['mailcontent'].smail;
        let subject = mailCnt['mailcontent'].subject;
        let content = mailCnt['mailcontent'].message;
        console.log(name)
        console.log(mail)
        console.log(subject)
        console.log(content)
        document.getElementById('mailview').innerHTML = mailviewcontent;
        document.getElementById("mailUname").innerHTML = name
        document.getElementById("mailUID").innerHTML = mail
        document.getElementById("mailSubject").innerHTML = subject
        document.getElementById("mailText").innerHTML = content
    }