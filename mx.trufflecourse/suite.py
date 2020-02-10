suite = {
  "mxversion" : "5.0",
  "name": "trufflecourse",

  "imports": {
    "suites": [
        {
        "name": "truffle",
        "subdir": True,
        "version": "a5d4ddddcb724626a5d92c009b2d8f7fa394a1e4"
      }
    ],
  },

  "projects": {
    "org.truffle.cs.samples": {
      "subDir": "code",
      "sourceDirs": ["src"],
      "dependencies": [
        "truffle:TRUFFLE_API"
      ],
      "annotationProcessors": ["truffle:TRUFFLE_DSL_PROCESSOR"],
      "javaCompliance": "1.8"
    },       
    "org.truffle.cs.lisp": {
      "subDir": "code",
      "sourceDirs": ["src"],
      "dependencies": [
        "truffle:TRUFFLE_API"
      ],
      "annotationProcessors": ["truffle:TRUFFLE_DSL_PROCESSOR"],
      "javaCompliance": "1.8"
    },       
    "org.truffle.cs.mj": {
      "subDir": "code",
      "sourceDirs": ["src"],
      "dependencies": [
        "truffle:TRUFFLE_API"
      ],
      "annotationProcessors": ["truffle:TRUFFLE_DSL_PROCESSOR"],
      "javaCompliance": "1.8"
    },       
  },

  "distributions": {
    "TruffleCourse": {
      "subDir": "code",
      "dependencies": [
		"org.truffle.cs.samples",
		"org.truffle.cs.mj",
                "org.truffle.cs.lisp"
	  ],
      "distDependencies": [
        "truffle:TRUFFLE_API",
      ]   
    }
  }
}
