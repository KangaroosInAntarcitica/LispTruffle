import tarfile
import os
from os.path import join
import shutil
import subprocess
from argparse import ArgumentParser

import mx
import mx_subst
import mx_sdk
import re

_suite = mx.suite('trufflecourse')

def getClasspathOptions(extra_dists=None):
    """gets the classpath of the Sulong distributions"""
    return mx.get_runtime_jvm_args((extra_dists or []))

def runLispInterpreter(args=None, out=None, get_classpath_options=getClasspathOptions):
    dists = ["TruffleCourse"]
    return mx.run_java(get_classpath_options(dists) + [
        "-XX:+EnableJVMCI",
        "-XX:+UseJVMCICompiler",
        "-Dgraal.TraceTruffleCompilation=true",
        # "-Dgraal.TruffleCompileImmediately=true",
        "-Dgraal.TruffleBackgroundCompilation=false"
        ] + 
    args + ["org.truffle.cs.lisp.main.LispRuntime"], out=out,jdk=mx.get_jdk())

def runLispScript(args=None, out=None, get_classpath_options=getClasspathOptions):
    dists = ["TruffleCourse"]
    return mx.run_java(get_classpath_options(dists) + [
        "-XX:+EnableJVMCI",
        "-XX:+UseJVMCICompiler",
        "-Dgraal.TraceTruffleCompilation=true",
        # "-Dgraal.TruffleCompileImmediately=true",
        "-Dgraal.TruffleBackgroundCompilation=false",
        "-Dgraal.PrintGraph=Network",
        "-Dgraal.Dump=Truffle:9"
        ] + 
    args + ["org.truffle.cs.lisp.main.LispRuntime", "sample.lisp"], out=out,jdk=mx.get_jdk())

mx.update_commands(_suite, {
    'lispInterpreter' : [runLispInterpreter, ''],
    'lispScript' : [runLispScript, '']
})
