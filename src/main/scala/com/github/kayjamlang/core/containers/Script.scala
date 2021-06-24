package com.github.kayjamlang.core.containers

import com.github.kayjamlang.core.exceptions.ParserException

@throws[ParserException]
class Script(container: Container) extends PackContainer("\\", container, true)
