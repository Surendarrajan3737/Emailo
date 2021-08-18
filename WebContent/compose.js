
const sendmail = async(formdat)=> {
    let tomail = document.getElementById("To").value;
    let mailsub = document.getElementById("Subject").value;
    let mailcnt = document.getElementById("mailContent").value;

    formdat.set("frommail", sessionStorage.getItem("Username"));
    formdat.set("tomail", tomail);
    formdat.set("mailsub", mailsub);
    formdat.set("mailcnt", mailcnt);
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