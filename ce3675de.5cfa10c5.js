(window.webpackJsonp=window.webpackJsonp||[]).push([[14],{151:function(e,n,t){"use strict";t.r(n),t.d(n,"frontMatter",(function(){return c})),t.d(n,"metadata",(function(){return l})),t.d(n,"rightToc",(function(){return p})),t.d(n,"default",(function(){return b}));var a=t(2),r=t(9),i=(t(0),t(158)),c={id:"semi-group",title:"Semi-Group"},l={id:"typeclass/semi-group",isDocsHomePage:!1,title:"Semi-Group",description:"Semi-Group",source:"@site/../generated-docs/target/mdoc/typeclass/semi-group.md",permalink:"/docs/typeclass/semi-group",sidebar:"someSidebar",previous:{title:"Typeclass",permalink:"/docs/typeclass/typeclass"},next:{title:"Monoid",permalink:"/docs/typeclass/monoid"}},p=[],o={rightToc:p};function b(e){var n=e.components,t=Object(r.a)(e,["components"]);return Object(i.b)("wrapper",Object(a.a)({},o,t,{components:n,mdxType:"MDXLayout"}),Object(i.b)("h1",{id:"semi-group"},"Semi-Group"),Object(i.b)("p",null,"A semi-group is a typeclass which can apply associative binary operation.\nSo if a type is a semi-group, its binary operation ",Object(i.b)("inlineCode",{parentName:"p"},"append")," can be applied.\nIt's associative so ",Object(i.b)("inlineCode",{parentName:"p"},"SemiGroup[A].append(SemiGroup[A].append(a, b), c)")," is\nequal to ",Object(i.b)("inlineCode",{parentName:"p"},"SemiGroup[A].append(a, SemiGroup[A].append(b, c))")),Object(i.b)("p",null,"e.g.)"),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"import just.fp._\n\ndef foo[A](x: Int, y: Int, f: Int => A)(implicit S: SemiGroup[A]): A =\n  S.append(f(x), f(y))\n\n// or with context bound\ndef foo[A: SemiGroup](x: Int, y: Int, f: Int => A): A =\n  SemiGroup[A].append(f(x), f(y))\n")),Object(i.b)("p",null,"If there is a typeclass instance of ",Object(i.b)("inlineCode",{parentName:"p"},"SemiGroup")," for a type ",Object(i.b)("inlineCode",{parentName:"p"},"A"),",\n",Object(i.b)("inlineCode",{parentName:"p"},"mappend")," method or a convenient ",Object(i.b)("inlineCode",{parentName:"p"},"|+|")," infix operator can be used like this."),Object(i.b)("p",null,"e.g.) There is already a SemiGroup typeclass instance for ",Object(i.b)("inlineCode",{parentName:"p"},"Int")," in ",Object(i.b)("inlineCode",{parentName:"p"},"just-fp"),"\nso you can do"),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"import just.fp.syntax._\n")),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"1.mappend(2)\n// res0: Int = 3\n\n1 |+| 2\n// res1: Int = 3\n")),Object(i.b)("p",null,"Typeclass instances for the following typeclasses are available in ",Object(i.b)("inlineCode",{parentName:"p"},"just-fp"),"."),Object(i.b)("ul",null,Object(i.b)("li",{parentName:"ul"},Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[List[A]]"),"  ")),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"List(1, 2, 3) |+| List(4, 5, 6)\n// res2: List[Int] = List(1, 2, 3, 4, 5, 6)\n\nList(1, 2, 3).mappend(List(4, 5, 6))\n// res3: List[Int] = List(1, 2, 3, 4, 5, 6)\n")),Object(i.b)("ul",null,Object(i.b)("li",{parentName:"ul"},Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[Vector[A]]"))),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"Vector(1, 2, 3) |+| Vector(4, 5, 6)\n// res4: Vector[Int] = Vector(1, 2, 3, 4, 5, 6)\n\nVector(1, 2, 3).mappend(Vector(4, 5, 6))\n// res5: Vector[Int] = Vector(1, 2, 3, 4, 5, 6)\n")),Object(i.b)("ul",null,Object(i.b)("li",{parentName:"ul"},Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[String]"))),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),'"abc" |+| "def"\n// res6: String = "abcdef"\n\n"abc".mappend("def")\n// res7: String = "abcdef"\n')),Object(i.b)("ul",null,Object(i.b)("li",{parentName:"ul"},Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[Byte]"))),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"1.toByte |+| 2.toByte\n// res8: Byte = 3\n\n1.toByte.mappend(2.toByte)\n// res9: Byte = 3\n")),Object(i.b)("ul",null,Object(i.b)("li",{parentName:"ul"},Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[Short]"))),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"1.toShort |+| 2.toShort\n// res10: Short = 3\n\n1.toShort.mappend(2.toShort)\n// res11: Short = 3\n")),Object(i.b)("ul",null,Object(i.b)("li",{parentName:"ul"},Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[Char]"))),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"'A' |+| '1'\n// res12: Char = 'r'\n\n'A'.mappend('1')\n// res13: Char = 'r'\n")),Object(i.b)("ul",null,Object(i.b)("li",{parentName:"ul"},Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[Int]"))),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"1 |+| 2\n// res14: Int = 3\n\n1.mappend(2)\n// res15: Int = 3\n")),Object(i.b)("ul",null,Object(i.b)("li",{parentName:"ul"},Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[Long]"))),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"1L |+| 2L\n// res16: Long = 3L\n\n1L.mappend(2L)\n// res17: Long = 3L\n")),Object(i.b)("ul",null,Object(i.b)("li",{parentName:"ul"},Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[BigInt]"))),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"BigInt(1) |+| BigInt(2)\n// res18: BigInt = 3\n\nBigInt(1).mappend(BigInt(2))\n// res19: BigInt = 3\n")),Object(i.b)("ul",null,Object(i.b)("li",{parentName:"ul"},Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[BigDecimal]"))),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"BigDecimal(1) |+| BigDecimal(2)\n// res20: BigDecimal = 3\n\nBigDecimal(1).mappend(BigDecimal(2))\n// res21: BigDecimal = 3\n")),Object(i.b)("ul",null,Object(i.b)("li",{parentName:"ul"},Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[Option[A]]")," if there is a typeclass instance of ",Object(i.b)("inlineCode",{parentName:"li"},"SemiGroup[A]"),".")),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"1.some |+| 2.some\n// res22: Option[Int] = Some(3)\n\n1.some.mappend(2.some)\n// res23: Option[Int] = Some(3)\n")),Object(i.b)("p",null,"NOTE: There might be an associativity issue with ",Object(i.b)("inlineCode",{parentName:"p"},"BigDecimal"),"\nif it's created with other ",Object(i.b)("inlineCode",{parentName:"p"},"MathContext")," than ",Object(i.b)("inlineCode",{parentName:"p"},"MathContext.UNLIMITED")," and\nthe number is big enough in Scala 2.13. "),Object(i.b)("p",null,"More about the issue, please read ",Object(i.b)("a",Object(a.a)({parentName:"p"},{href:"https://blog.kevinlee.io/2019/09/29/be-careful-when-using-bigdecimal-in-scala-2.13"}),"this blog"),"."))}b.isMDXComponent=!0},158:function(e,n,t){"use strict";t.d(n,"a",(function(){return s})),t.d(n,"b",(function(){return O}));var a=t(0),r=t.n(a);function i(e,n,t){return n in e?Object.defineProperty(e,n,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[n]=t,e}function c(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);n&&(a=a.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,a)}return t}function l(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?c(Object(t),!0).forEach((function(n){i(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):c(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}function p(e,n){if(null==e)return{};var t,a,r=function(e,n){if(null==e)return{};var t,a,r={},i=Object.keys(e);for(a=0;a<i.length;a++)t=i[a],n.indexOf(t)>=0||(r[t]=e[t]);return r}(e,n);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(a=0;a<i.length;a++)t=i[a],n.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(r[t]=e[t])}return r}var o=r.a.createContext({}),b=function(e){var n=r.a.useContext(o),t=n;return e&&(t="function"==typeof e?e(n):l(l({},n),e)),t},s=function(e){var n=b(e.components);return r.a.createElement(o.Provider,{value:n},e.children)},u={inlineCode:"code",wrapper:function(e){var n=e.children;return r.a.createElement(r.a.Fragment,{},n)}},m=r.a.forwardRef((function(e,n){var t=e.components,a=e.mdxType,i=e.originalType,c=e.parentName,o=p(e,["components","mdxType","originalType","parentName"]),s=b(t),m=a,O=s["".concat(c,".").concat(m)]||s[m]||u[m]||i;return t?r.a.createElement(O,l(l({ref:n},o),{},{components:t})):r.a.createElement(O,l({ref:n},o))}));function O(e,n){var t=arguments,a=n&&n.mdxType;if("string"==typeof e||a){var i=t.length,c=new Array(i);c[0]=m;var l={};for(var p in n)hasOwnProperty.call(n,p)&&(l[p]=n[p]);l.originalType=e,l.mdxType="string"==typeof e?e:a,c[1]=l;for(var o=2;o<i;o++)c[o]=t[o];return r.a.createElement.apply(null,c)}return r.a.createElement.apply(null,t)}m.displayName="MDXCreateElement"}}]);