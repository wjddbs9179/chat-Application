function chatRoomAddMember(count) {
    var button = event.target;
    var memberName = button.closest('tr').querySelector('td:nth-child(2)').textContent;

    console.log(count);

    var input = document.createElement('input');
    input.setAttribute('type', 'text');
    input.setAttribute('name', 'members');
    input.value = memberName;

    var chatRoomMemberBox = document.getElementById('chatRoomMemberBox');
    chatRoomMemberBox.appendChild(input);
}