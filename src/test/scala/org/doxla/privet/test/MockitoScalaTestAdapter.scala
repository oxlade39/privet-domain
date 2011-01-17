package org.doxla.privet.test

import org.specs.specification.DefaultLifeCycle


trait MockitoScalaTestAdapter {
  def lastExample = None
  def forExample = DefaultLifeCycle
}