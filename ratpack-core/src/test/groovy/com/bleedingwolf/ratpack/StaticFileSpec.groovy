/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bleedingwolf.ratpack

class StaticFileSpec extends RatpackSpec {

  def "can serve static file"() {
    given:
    publicFile("static.text") << "hello!"

    when:
    app.start()

    then:
    urlText("static.text") == "hello!"
  }

  def "handlers work with static files"() {
    given:
    publicFile("static.text") << "hello!"
    ratpackFile << """
      get("/") { renderString "foo" }
    """

    when:
    app.start()

    then:
    urlText("static.text") == "hello!"
    urlText() == "foo"

    when:
    ratpackFile << """
      get("/static.text") { renderString "bar" }
    """

    then:
    urlText("static.text") == "hello!"
  }

}
