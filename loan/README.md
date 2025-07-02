build : ./gradlew shadowJar
run : java -jar loan-calculator.jar <대출금> <이자율> <상환개월> <상환방법>
상환방법enum : EMI(원리금균등상환), BULLET_LOAN(만기일시상환)