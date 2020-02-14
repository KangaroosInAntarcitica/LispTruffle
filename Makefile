JAVAHOME = ../openjdk1.8.0_232-jvmci-19.3-b06
MX = ../mx/mx

build:
	env JAVA_HOME=$(JAVAHOME) $(MX) --dy /compiler  --jdk jvmci build

run:
	env JAVA_HOME=$(JAVAHOME) $(MX) --dy /compiler  --jdk jvmci lispInterpreter
