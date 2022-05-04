# Formula
계산을하게 해주는 라이브러리 입니다.</br>

## Jar 파일 다운로드 링크
  [모든 버전 다운로드](https://downgit.evecalm.com/#/home?url=https://github.com/PersesTitan/Formula/Jar)</br>
  [V1 다운로드](https://downgit.evecalm.com/#/home?url=https://github.com/PersesTitan/Formula/Jar/V1)</br>
  [V2 다운로드](https://downgit.evecalm.com/#/home?url=https://github.com/PersesTitan/Formula/Jar/V2)</br>
  [V3 다운로드](https://downgit.evecalm.com/#/home?url=https://github.com/PersesTitan/Formula/Jar/V3)</br>
  
# 사용법
라이브러리 들어가서 jar파일을 추가해 주세요.
  

# 생성자
## Formula formula = new Formula(); </br>
설명 : 기본 값이 적용됩니다. (기본 값은 하단에 정리되있음)</br>
</br>
## Formula formula = new Formula(String plus, String minus, String multiple, String division); </br>
설명 : +, -, *, / 를 직접 설정할 수 있습니다. </br>
리턴타입 : void </br>
  * plus = 더하기를 정의하는 변수 (기본값 : +)
  * minus = 빼기를 정의하는 변수 (기본값 : -)
  * multiple = 곱하기를 정의하는 변수 (기본값 : *)
  * division = 나누기를 정의하는 변수 (기본값 : /)

</br>

---

</br>


## formula(String line, boolean operation);
  
설명 : 계산를 한뒤 계산값을 리턴해줍니다.</br>
리턴타입 : double</br>
  * line = 연산식을 입력하는 변수
  * operation = true 입력시 곱하기와 나누기를 먼저 계산한 후 값을 반환합니다. </br>
                          false 입력시 순서대로 계산한 후 값을 반환합니다.
                          
                          
# 버전
  1. 2022/5/2 V1 - formula 추가
  2. 2022/5/2 V2 - 음수 계산 문제해결
  3. 2022/5/4 V3 - 생성자로 변경
