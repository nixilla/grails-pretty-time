package org.grails.prettytime

import grails.plugins.Plugin

class PrettyTimeGrailsPlugin extends Plugin {
	def grailsVersion = "3.0.0 > *"
	def title = "Pretty-time plugin"
	def author = "Cazacu Mihai"
	def authorEmail = "cazacugmihai@gmail.com"
	def description = 'Allows you to display human readable, relative timestamps.'
	def documentation = "https://github.com/cazacugmihai/grails-pretty-time/blob/master/README.md"
	def license = "APACHE"
	def developers = [
		[name: "Janusz Slota", email: "janusz.slota@nixilla.com"],
		[name: "Diego Toharia", email: "diego@toharia.com"],
		[name: "Stefan Glase", email: "stefan.glase@googlemail.com"]
	]
	def issueManagement = [url: "https://github.com/cazacugmihai/grails-pretty-time/issues"]
	def scm = [url: "https://github.com/cazacugmihai/grails-pretty-time"]
}
