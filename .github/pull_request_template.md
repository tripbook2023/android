## 요약
Terms 레이아웃 Navigation에 추가, NicknameFragment에서 OnGlobalLayout 리스너 관련 에러 수정
## 변경점
OnGlobalLayout 리스너가 화면을 떠나기전에 remove되지 않으면 binding 관련 null 에러가 발생해서 onStop 메서드에서 remove하는 걸로 수정
## 참고사항