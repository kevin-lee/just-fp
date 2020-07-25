(window.webpackJsonp=window.webpackJsonp||[]).push([[15],{152:function(e,t,a){"use strict";a.r(t),a.d(t,"frontMatter",(function(){return c})),a.d(t,"metadata",(function(){return i})),a.d(t,"rightToc",(function(){return s})),a.d(t,"default",(function(){return l}));var n=a(2),r=a(9),o=(a(0),a(158)),c={id:"getting-started",title:"Getting Started"},i={id:"getting-started",isDocsHomePage:!0,title:"Getting Started",description:"just-fp",source:"@site/../generated-docs/target/mdoc/getting-started.md",permalink:"/docs/",sidebar:"someSidebar",next:{title:"Syntax",permalink:"/docs/syntax/syntax"}},s=[{value:" just-fp",id:"just-fp",children:[]},{value:"Getting Started",id:"getting-started",children:[]}],p={rightToc:s};function l(e){var t=e.components,a=Object(r.a)(e,["components"]);return Object(o.b)("wrapper",Object(n.a)({},p,a,{components:t,mdxType:"MDXLayout"}),Object(o.b)("h2",{id:"just-fp"},Object(o.b)("img",Object(n.a)({parentName:"h2"},{src:"../img/just-fp-logo-64x64.png",alt:null}))," just-fp"),Object(o.b)("p",null,Object(o.b)("a",Object(n.a)({parentName:"p"},{href:"https://github.com/Kevin-Lee/just-fp/actions?workflow=Build+All"}),Object(o.b)("img",Object(n.a)({parentName:"a"},{src:"https://github.com/Kevin-Lee/just-fp/workflows/Build%20All/badge.svg",alt:"Build Status"}))),"\n",Object(o.b)("a",Object(n.a)({parentName:"p"},{href:"https://github.com/Kevin-Lee/just-fp/actions?workflow=Release"}),Object(o.b)("img",Object(n.a)({parentName:"a"},{src:"https://github.com/Kevin-Lee/just-fp/workflows/Release/badge.svg",alt:"Release Status"}))),"\n",Object(o.b)("a",Object(n.a)({parentName:"p"},{href:"https://coveralls.io/github/Kevin-Lee/just-fp?branch=main"}),Object(o.b)("img",Object(n.a)({parentName:"a"},{src:"https://coveralls.io/repos/github/Kevin-Lee/just-fp/badge.svg?branch=main",alt:"Coverage Status"})))),Object(o.b)("p",null,Object(o.b)("a",Object(n.a)({parentName:"p"},{href:"https://bintray.com/kevinlee/maven/just-fp/_latestVersion"}),Object(o.b)("img",Object(n.a)({parentName:"a"},{src:"https://api.bintray.com/packages/kevinlee/maven/just-fp/images/download.svg",alt:"Download"}))),"\n",Object(o.b)("a",Object(n.a)({parentName:"p"},{href:"https://search.maven.org/artifact/io.kevinlee/just-fp_2.13"}),Object(o.b)("img",Object(n.a)({parentName:"a"},{src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/just-fp_2.13/badge.svg",alt:"Maven Central"}))),"\n",Object(o.b)("a",Object(n.a)({parentName:"p"},{href:"https://index.scala-lang.org/kevin-lee/just-fp/just-fp"}),Object(o.b)("img",Object(n.a)({parentName:"a"},{src:"https://index.scala-lang.org/kevin-lee/just-fp/just-fp/latest.svg",alt:"Latest version"})))),Object(o.b)("p",null,"just-fp is a small Functional Programming library. This is not meant to be an alternative to Scalaz or Cats. The reason for having this library is that in your project you don't want to have Scalaz or Cats as its dependency and you only need much smaller set of functional programming features than what Scalaz or Cats offers. So the users of your library can choose Scalaz or Cats to be used with your library."),Object(o.b)("h2",{id:"getting-started"},"Getting Started"),Object(o.b)("p",null,"In ",Object(o.b)("inlineCode",{parentName:"p"},"build.sbt"),","),Object(o.b)("pre",null,Object(o.b)("code",Object(n.a)({parentName:"pre"},{className:"language-scala"}),'libraryDependencies += "io.kevinlee" %% "just-fp" % "1.3.5"\n')),Object(o.b)("p",null,"then import"),Object(o.b)("pre",null,Object(o.b)("code",Object(n.a)({parentName:"pre"},{className:"language-scala"}),"import just.fp._\nimport just.fp.syntax._\n")),Object(o.b)("p",null,"or "),Object(o.b)("pre",null,Object(o.b)("code",Object(n.a)({parentName:"pre"},{className:"language-scala"}),"import just.fp._, syntax._\n")),Object(o.b)("p",null,"In all the example code using ",Object(o.b)("inlineCode",{parentName:"p"},"just-fp")," below, I assume that you've already imported ",Object(o.b)("inlineCode",{parentName:"p"},"just-fp")," at the top."))}l.isMDXComponent=!0},158:function(e,t,a){"use strict";a.d(t,"a",(function(){return b})),a.d(t,"b",(function(){return f}));var n=a(0),r=a.n(n);function o(e,t,a){return t in e?Object.defineProperty(e,t,{value:a,enumerable:!0,configurable:!0,writable:!0}):e[t]=a,e}function c(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function i(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?c(Object(a),!0).forEach((function(t){o(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):c(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function s(e,t){if(null==e)return{};var a,n,r=function(e,t){if(null==e)return{};var a,n,r={},o=Object.keys(e);for(n=0;n<o.length;n++)a=o[n],t.indexOf(a)>=0||(r[a]=e[a]);return r}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(n=0;n<o.length;n++)a=o[n],t.indexOf(a)>=0||Object.prototype.propertyIsEnumerable.call(e,a)&&(r[a]=e[a])}return r}var p=r.a.createContext({}),l=function(e){var t=r.a.useContext(p),a=t;return e&&(a="function"==typeof e?e(t):i(i({},t),e)),a},b=function(e){var t=l(e.components);return r.a.createElement(p.Provider,{value:t},e.children)},u={inlineCode:"code",wrapper:function(e){var t=e.children;return r.a.createElement(r.a.Fragment,{},t)}},m=r.a.forwardRef((function(e,t){var a=e.components,n=e.mdxType,o=e.originalType,c=e.parentName,p=s(e,["components","mdxType","originalType","parentName"]),b=l(a),m=n,f=b["".concat(c,".").concat(m)]||b[m]||u[m]||o;return a?r.a.createElement(f,i(i({ref:t},p),{},{components:a})):r.a.createElement(f,i({ref:t},p))}));function f(e,t){var a=arguments,n=t&&t.mdxType;if("string"==typeof e||n){var o=a.length,c=new Array(o);c[0]=m;var i={};for(var s in t)hasOwnProperty.call(t,s)&&(i[s]=t[s]);i.originalType=e,i.mdxType="string"==typeof e?e:n,c[1]=i;for(var p=2;p<o;p++)c[p]=a[p];return r.a.createElement.apply(null,c)}return r.a.createElement.apply(null,a)}m.displayName="MDXCreateElement"}}]);