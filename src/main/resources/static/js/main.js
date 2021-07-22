initUploadFace(DEFAULT_TEMPLATE_ID);
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
//		e.target // newly activated tab
//		e.relatedTarget // previous active tab
  const {id} = e.target;
  if (id) {
    $('#template').val(id)
    initUploadFace(id)
  }
})
$('#render').bind('click', async function () {
  var $btn = $(this).button('loading')
  $('#render').prop('disabled', true);
  var template = $('#template').val()
  var inputs = $("input[name='" + template + "_sen']")
  var all = [];
  var ischange = false;
  inputs.each(function (i, e) {
    e.value = e.value === '' ? e.placeholder : e.value
    if (e.value !== e.placeholder) {
      ischange = true;
    }
    all.push(e.value)
  })
  // if (!ischange) {
  //   // alert("生成成功！欢迎扫码关注公众号！");
  //   $('#download').attr('href', "http://cdn.txtxtx.com.cn/" + template + ".gif");
  //   $btn.button('reset')
  //   $('#render').prop('disabled', false);
  //   $('#downloadbtn').removeClass('hidden')
  //   return;
  // }
  
  const requestData = {
    "templateName": template,
    "sentence": all.join(),
    "mode": $("input:checked").val()
  }
  
  const formData = new FormData();
  Object.keys(requestData).forEach(key => {
    formData.append(key, requestData[key]);
  });
  const defaultImg = await fetch(DEFAULT_IMAGE_URL).then(res => res.blob());
  $(`div[data-template=${template}]>input`).each((_1, $input) => {
    formData.append('imageList', $input.files[0] ?? defaultImg);
  })
  
  await fetch(`${api.baseUrl}/gif/filePath`, {
    method: 'POST',
    body: formData,
  })
    .then(res => res.json())
    .then(msg => {
      if (msg.code === '0') {
        alert("生成成功！快去看看吧！");
        $('#download').attr('href', `${api.baseUrl}/${msg.result}`);
        $('#downloadbtn').removeClass('hidden')
      } else {
        alert("服务器爆掉了，请稍后再试！");
// $('#result').attr('src', 'http://cdn.txtxtx.com.cn/money.jpg');
      }
    })
    .catch(error => {
      alert("服务器爆掉了，请稍后再试！赞赏给作者点鼓励吧。");
    })
    .finally(() => {
      $btn.button('reset')
      $('#render').prop('disabled', false);
    })
});
//	$('#result').load(function() {
//		$('#render').button('reset')
//		$('#render').prop('disabled', false);
//	});
