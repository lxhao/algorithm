dnl                                               -*- Autoconf -*-
dnl Process this file with autoconf to produce a configure script.
dnl
dnl

AC_PREREQ(2.59)
AC_INIT(pserv, 3.4, rmottola@users.sf.net)
AC_CONFIG_SRCDIR([sources/handlers.c])
AC_CONFIG_HEADER([sources/config.h])
AM_INIT_AUTOMAKE([1.8.0])

AM_MAINTAINER_MODE

dnl
dnl Checks for programs.
dnl
AC_PROG_CC
AC_PROG_INSTALL

dnl configure options
AC_ARG_ENABLE(forking,
AC_HELP_STRING([--enable-forking], [builds parallel server (fork after request)]),
ac_forking_request=$enableval,
ac_forking_request=yes)
if test $ac_forking_request = yes; then
    AC_DEFINE(FORKING_REQUEST, 1, [if the server forks at each request or not])
fi

AC_ARG_ENABLE(cgi,
AC_HELP_STRING([--enable-cgi], [enable/disable CGI support]),
ac_enable_cgi=$enableval,
ac_enable_cgi=yes)
if test $ac_enable_cgi = yes; then
    AC_DEFINE(ENABLE_CGI, 1, [if cgi script support is enabled])
fi

dnl Checks for libraries.

dnl Checks for header files.
AC_HEADER_DIRENT
AC_HEADER_STDC
AC_HEADER_SYS_WAIT
AC_CHECK_HEADERS([arpa/inet.h netdb.h netinet/in.h sys/socket.h sys/time.h])

dnl Checks for typedefs, structures, and compiler characteristics.
AC_TYPE_OFF_T
AC_TYPE_SIZE_T
AC_HEADER_TIME
AC_STRUCT_TM

dnl check for socklen_t
TYPE_SOCKLEN_T

dnl Checks for library functions.
AC_FUNC_FORK
AC_TYPE_SIGNAL
AC_FUNC_STAT
AC_FUNC_STRFTIME
dnl handle memmove better with replacement
AC_CHECK_FUNCS([dup2 inet_ntoa memmove socket strstr])


AC_CONFIG_FILES([ \
Makefile \
defaults/Makefile \
sources/Makefile \
])
AC_OUTPUT()
