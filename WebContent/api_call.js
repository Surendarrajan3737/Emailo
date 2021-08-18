var subscriptionKey = "f90cc7ef8f244f5d8a4a789a31b61e2e";
var uriBaseDetect =
    "https://thundermail.cognitiveservices.azure.com//face/v1.0/detect";
var uriBaseVerify =
    "https://thundermail.cognitiveservices.azure.com//face/v1.0/verify";

// Request parameters.
var detectParams = {
    "detectionModel": "detection_01",
    "returnFaceId": "true"
};
const returnFaceID = async (ImageUrl) => {


    const response = await fetch(uriBaseDetect, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Ocp-Apim-Subscription-Key': subscriptionKey,
        },
        body: JSON.stringify({ url: ImageUrl })

    }).then((data) => data.json())
        .then((data) => {
            console.log(data)
            return data;
        })
        .then((data) => data)
        .catch((err) => {
            console.log(err)
        })
    // document.getElementById("responseTextArea").innerHTML(JSON.stringify(response))
    console.log(response[0])
    return response[0]
}



const returnFaceID1 = async (ImageUrl) => {


    const response = await fetch(uriBaseDetect, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/octet-stream',
            'Ocp-Apim-Subscription-Key': subscriptionKey,
        },
        body: ImageUrl

    }).then((data) => data.json())
        .then((data) => {
            console.log(data)
            return data;
        })
        .then((data) => data)
        .catch((err) => {
            console.log(err)
        })
    // document.getElementById("responseTextArea").innerHTML(JSON.stringify(response))
    console.log(response[0])
    return response[0]
}

const returnFaceMatch = async (faceID1, faceID2) => {
    const response = await fetch(uriBaseVerify, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Ocp-Apim-Subscription-Key': subscriptionKey,
        },
        body: JSON.stringify({
            faceId1: faceID1,
            faceId2: faceID2,
        })
    }).then((data) => data.json())
        .then((data) => {
            console.log(data)
            return data;
        })
        .then((data) => data)
        .catch((err) => {
            console.log(err)
        })
    console.log(response);
    return response;
}