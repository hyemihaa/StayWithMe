(function() {

<<<<<<< HEAD
  const parent = document.querySelector(".range-slider");
  if(!parent) return;

  const
    rangeS = parent.querySelectorAll("input[type=range]"), // 범위 슬라이더
    numberS = parent.querySelectorAll("input[type=number]"); // 값 슬라이더

//// 슬라이더 값이 변경될 때마다 데이터를 불러오는 함수
//  function fetchData(slide1, slide2) {
//    console.log("슬라이더 범위:", slide1, "부터", slide2, "까지");
//
//    // AJAX 요청을 통해 서버에서 데이터를 가져오는 예시
//    fetch(`/getData?min=${slide1}&max=${slide2}`)
//      .then(response => response.json())
//      .then(data => {
//        console.log('서버에서 가져온 데이터:', data);
//        // 데이터를 사용하여 업데이트 작업을 수행
//      })
//      .catch(error => console.error('데이터 가져오기 에러:', error));
//  }

  rangeS.forEach(function(el) {
    el.oninput = function() {
      let slide1 = parseFloat(rangeS[0].value), // 좌측 슬라이더
          slide2 = parseFloat(rangeS[1].value); // 우측 슬라이더

      if (this === rangeS[0] && slide1 >= slide2) { // 좌측 슬라이더가 우측 슬라이더를 넘어가려 할 때
        slide1 = slide2 - 50000; // 슬라이더가 겹치지 않도록 50000만큼 차이 둠
      } else if (this === rangeS[1] && slide2 <= slide1) { // 우측 슬라이더가 좌측 슬라이더를 넘어가려 할 때
        slide2 = slide1 + 50000; // 슬라이더가 겹치지 않도록 50000만큼 차이 둠
      }

      rangeS[0].value = slide1;
      rangeS[1].value = slide2;

      numberS[0].value = slide1;
      numberS[1].value = slide2;

=======
  var parent = document.querySelector(".range-slider");
  if(!parent) return;

  var
    rangeS = parent.querySelectorAll("input[type=range]"),
    numberS = parent.querySelectorAll("input[type=number]");

  rangeS.forEach(function(el) {
    el.oninput = function() {
      var slide1 = parseFloat(rangeS[0].value),
        	slide2 = parseFloat(rangeS[1].value);

      if (slide1 > slide2) {
				[slide1, slide2] = [slide2, slide1];
        // var tmp = slide2;
        // slide2 = slide1;
        // slide1 = tmp;
      }

      numberS[0].value = slide1;
      numberS[1].value = slide2;
>>>>>>> 02fc4bf57dd32e0f2f5c1d5ea0636e3ad9abb9cf
    }
  });

  numberS.forEach(function(el) {
    el.oninput = function() {
<<<<<<< HEAD
      let number1 = parseFloat(numberS[0].value),
          number2 = parseFloat(numberS[1].value);

      if (number1 >= number2) {
        number1 = number2 - 1; // 숫자가 겹치지 않도록 1만큼 차이 둠
      }

      numberS[0].value = number1;
      numberS[1].value = number2;

      rangeS[0].value = number1;
      rangeS[1].value = number2;
    }
  });

})();
=======
			var number1 = parseFloat(numberS[0].value),
					number2 = parseFloat(numberS[1].value);
			
      if (number1 > number2) {
        var tmp = number1;
        numberS[0].value = number2;
        numberS[1].value = tmp;
      }

      rangeS[0].value = number1;
      rangeS[1].value = number2;

    }
  });

})();
>>>>>>> 02fc4bf57dd32e0f2f5c1d5ea0636e3ad9abb9cf
