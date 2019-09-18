package just.fp

import just.fp.syntax.{EitherSyntax, EqualSyntax, SemiGroupSyntax}

/**
  * @author Kevin Lee
  * @since 2019-05-26
  */
object JustSyntax extends EqualSyntax with EitherSyntax with SemiGroupSyntax
