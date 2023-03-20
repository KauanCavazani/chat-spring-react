import React, { useEffect, useState } from 'react'
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';

var stompClient =null;
const ChatRoom = () => {

    const [privateChats, setPrivateChats] = useState(new Map());     
    const [publicChats, setPublicChats] = useState([]); 

    const [tab,setTab] =useState("CHATROOM");

    const [userData, setUserData] = useState({
        username: null,
        receivername: null,
        connected: false,
        message: null
    });

    useEffect(() => {}, [userData]);

    const connect = () => {
        if(userData.username !== null && userData.username !== "") {
            let Sock = new SockJS('http://localhost:8080/ws');
            stompClient = over(Sock);
            stompClient.connect({}, onConnected, onError);
        }
    }

    const onError = (err) => {
        console.error(err);
    }

    const onConnected = () => {
        setUserData({...userData,"connected": true});
        stompClient.subscribe('/chatroom/public', onMessageReceived);
        stompClient.subscribe('/user/'+userData.username+'/private', onPrivateMessage);
        userJoin();
    }

    const userJoin = () => {
        var chatMessage = {
            senderName: userData.username,
            status:"JOIN"
        };
        stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
    }

    const onMessageReceived = (payload) => {
        var payloadData = JSON.parse(payload.body);
        switch(payloadData.status){
            case "JOIN":
                if(!privateChats.get(payloadData.senderName)){
                    privateChats.set(payloadData.senderName,[]);
                    setPrivateChats(new Map(privateChats));
                }
                break;
            case "MESSAGE":
                publicChats.push(payloadData);
                setPublicChats([...publicChats]);
                scrollToDown();
                break;
            default:
                break;
        }
    }
    
    const onPrivateMessage = (payload) => {
        var payloadData = JSON.parse(payload.body);
        if(privateChats.get(payloadData.senderName)){
            privateChats.get(payloadData.senderName).push(payloadData);
            setPrivateChats(new Map(privateChats));
        } else {
            let list = [];
            list.push(payloadData);
            privateChats.set(payloadData.senderName,list);
            setPrivateChats(new Map(privateChats));
        }
    }

    const handleMessage = (event) => {
        const {value} = event.target;
        setUserData({...userData,"message": value});
        isValidMessage(value);
    }

    const sendPublicValue = () => {
        if (stompClient) {
            var chatMessage = {
                senderName: userData.username,
                message: userData.message,
                status: "MESSAGE"
            };

            if(isValidMessage(chatMessage.message)) {
                stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
                setUserData({...userData,"message": ""});
            }
        }
    }

    const sendPrivateValue = () => {
        if (stompClient) {
            var chatMessage = {
                senderName: userData.username,
                receiverName: tab,
                message: userData.message,
                status: "MESSAGE"
            };

            if(userData.username !== tab) {
                privateChats.get(tab).push(chatMessage);
                setPrivateChats(new Map(privateChats));
            }

            if(isValidMessage(chatMessage.message)) {
                stompClient.send("/app/private-message", {}, JSON.stringify(chatMessage));
                setUserData({...userData,"message": ""});
            }
        }
    }

    const handleUsername = (event) => {
        const {value} = event.target;
        setUserData({...userData,"username": value});
    }

    const scrollToDown = () => {
        document.getElementById("areaPublicChat").scrollTop = document.getElementById("areaPublicChat").scrollHeight - 100;
        console.log(document.getElementById("areaPublicChat").scrollTop, document.getElementById("areaPublicChat").scrollHeight)
    }

    const isValidMessage = (message) => {
        if (message !== null && message !== "") {
            document.getElementById('sendBtn').classList.remove('text-muted');
            return true;
        }

        document.getElementById('sendBtn').classList.add('text-muted');
        return false;
    }

    return (
        <div className="container py-5">
            {userData.connected?
                <div className='row w-100'>
                    <div className='col-md-12'>
                        <div className="card mb-5" id="chat3">
                            <div className="card-body">
                                <div className='row h-100'>
                                    <div className="col-md-6 col-lg-5 col-xl-4 mb-4 mb-md-0 h-100">
                                        <div className="p-3">
                                            <div className='member-list' data-mdb-perfect-scrollbar="true">
                                                <ul className="list-unstyled mb-0">
                                                    <li onClick={()=>{setTab("CHATROOM")}} className={`p-2 border-bottom ${tab === "CHATROOM" && "active"}`}>
                                                        <a href="#!" className="d-flex justify-content-between">
                                                            <div className="d-flex flex-row">
                                                                <div>
                                                                    <img
                                                                        src="https://cdn-icons-png.flaticon.com/512/1910/1910995.png"
                                                                        alt="avatar" className="d-flex align-self-center me-3" width="60" 
                                                                    />
                                                                </div>
                                                                <div className="pt-3">
                                                                    <p className="fw-bold mb-0">Sala de Bate Papo</p>
                                                                </div>
                                                            </div>
                                                        </a>
                                                    </li>
                                                    {[...privateChats.keys()].map((name, index) => ( 
                                                        name !== userData.username &&
                                                            <li onClick={()=>{setTab(name)}} className={`p-2 border-bottom ${tab === name && "active"}`} key={index}>
                                                            <a href="#!" className="d-flex justify-content-between">
                                                                <div className="d-flex flex-row">
                                                                    <div>
                                                                        <img
                                                                            src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
                                                                            alt="avatar" className="d-flex align-self-center me-3" width="60" 
                                                                        />
                                                                    </div>
                                                                    <div className="pt-3">
                                                                        <p className="fw-bold mb-0">{name}</p>
                                                                    </div>
                                                                </div>
                                                                {/* <div className="pt-1">
                                                                    <p className="small text-muted mb-1">Just now</p>
                                                                    <span className="badge bg-danger rounded-pill float-end">3</span>
                                                                </div> */}
                                                            </a>
                                                        </li>
                                                    ))}
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div className='area-messages col-md-6 col-lg-7 col-xl-7 h-100'>
                                        <div className="pt-3 pe-3 member-list h-100">
                                            <div className='area-chat' id='areaPublicChat'>
                                            {tab === "CHATROOM" ?
                                                <ul className='list-unstyled h-100'>
                                                    {publicChats.map((chat, index) => (
                                                        <li className="message-list d-flex justify-content-between mb-4" key={index}>
                                                            {chat.senderName !== userData.username &&
                                                                <img 
                                                                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp" alt="avatar"
                                                                    className="rounded-circle d-flex align-self-start me-3 shadow-1-strong" width="60" 
                                                                />
                                                            }
                                                            <div className="card">
                                                                <div className="card-header d-flex justify-content-between p-3">
                                                                    <p className="fw-bold mb-0">{chat.senderName}</p>
                                                                </div>
                                                                <div className="card-body">
                                                                    <p className="mb-0">{chat.message}</p>
                                                                </div>
                                                            </div>
                                                            {chat.senderName === userData.username &&
                                                                <img 
                                                                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp" alt="avatar"
                                                                    className="rounded-circle d-flex align-self-start me-3 shadow-1-strong" width="60" 
                                                                />
                                                            }
                                                        </li>
                                                    ))}
                                                </ul>
                                                :
                                                <ul className='list-unstyled h-100'>
                                                    {[...privateChats.get(tab)].map((chat, index) => (
                                                        <li className="message-list d-flex justify-content-between mb-4" key={index}>
                                                            {chat.senderName !== userData.username &&
                                                                <img 
                                                                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp" alt="avatar"
                                                                    className="rounded-circle d-flex align-self-start me-3 shadow-1-strong" width="60" 
                                                                />
                                                            }
                                                            <div className="card">
                                                                <div className="card-header d-flex justify-content-between p-3">
                                                                    <p className="fw-bold mb-0">{chat.senderName}</p>
                                                                </div>
                                                                <div className="card-body">
                                                                    <p className="mb-0">{chat.message}</p>
                                                                </div>
                                                            </div>
                                                            {chat.senderName === userData.username &&
                                                                <img 
                                                                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp" alt="avatar"
                                                                    className="rounded-circle d-flex align-self-start me-3 shadow-1-strong" width="60" 
                                                                />
                                                            }
                                                        </li>
                                                    ))}
                                                </ul>
                                            }
                                            </div>
                                            <div className="text-muted d-flex justify-content-start align-items-center pe-3 pt-3 mt-2">
                                                <img 
                                                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
                                                    alt="avatar 3" width="40px" height="100%" 
                                                />
                                                <input 
                                                    type="text" 
                                                    className="form-control form-control-lg" 
                                                    id="exampleFormControlInput2"
                                                    placeholder="Escreva aqui..." 
                                                    value={userData.message}
                                                    onChange={handleMessage}
                                                    required
                                                />
                                                <a className="ms-1 text-muted" href="#!"><i className="bi bi-paperclip"></i></a>
                                                <a className="ms-3 text-muted" href="#!"><i className="bi bi-emoji-smile-fill"></i></a>
                                                <a className="ms-3 text-muted" href="#!" id='sendBtn' onClick={tab === "CHATROOM" ? sendPublicValue : sendPrivateValue}><i className="bi bi-send-fill"></i></a>
                                                </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                

            :

                <div className='card w-50 m-auto'>
                    <div className='card-body'>
                        <div className='card-title mb-2'>Login</div>
                        <div className='card-text'>
                            <div className='form-group'>
                                <label>Nome</label>
                                <input
                                    id="user-name"
                                    className='form-control'
                                    placeholder="Digite seu nome..."
                                    name="userName"
                                    value={userData.username}
                                    onChange={handleUsername}
                                    required
                                />
                            </div>
                            <button type="submit" className='btn btn-primary w-100 mt-3' onClick={connect}>
                                Conectar
                            </button> 
                        </div>
                    </div>
                </div>
            }
        </div>
    )
}

export default ChatRoom;