package org.vaadin.chronographer;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.ApplicationServlet;
import com.vaadin.ui.Window;

public class ChronographerServlet extends ApplicationServlet {

    @Override
    protected void writeAjaxPageHtmlVaadinScripts(Window window,
            String themeName, Application application, BufferedWriter page,
            String appUrl, String themeUri, String appId,
            HttpServletRequest request) throws ServletException, IOException {

        // request widgetset takes precedence (e.g portlet include)
        String requestWidgetset = (String) request
                .getAttribute(REQUEST_WIDGETSET);
        String sharedWidgetset = (String) request
                .getAttribute(REQUEST_SHARED_WIDGETSET);
        if (requestWidgetset == null && sharedWidgetset == null) {
            // Use the value from configuration or DEFAULT_WIDGETSET.
            // If no shared widgetset is specified, the default widgetset is
            // assumed to be in the servlet/portlet itself.
            requestWidgetset = getApplicationOrSystemProperty(
                    PARAMETER_WIDGETSET, DEFAULT_WIDGETSET);
        }

        String widgetset;
        String widgetsetBasePath;
        if (requestWidgetset != null) {
            widgetset = requestWidgetset;
            widgetsetBasePath = getWebApplicationsStaticFileLocation(request);
        } else {
            widgetset = sharedWidgetset;
            widgetsetBasePath = getStaticFilesLocation(request);
        }

        widgetset = stripSpecialChars(widgetset);

        final String filePath1 = widgetsetBasePath + "/"
                + WIDGETSET_DIRECTORY_PATH + widgetset
                + "/js/api/timeline-api.js";

        final String filePath2 = widgetsetBasePath + "/"
                + WIDGETSET_DIRECTORY_PATH + widgetset
                + "/ajax/api/simile-ajax-api.js";

        page.write("<script>");
        page.write("  Timeline_ajax_url=\"" + filePath2 + "\"");
        page.write("</script>\n");

        page.write("<script language='javascript' src='" + filePath1
                + "'></script>\n");

        super.writeAjaxPageHtmlVaadinScripts(window, themeName, application,
                page, appUrl, themeUri, appId, request);
    }

    /**
     * The default method to fetch static files location (URL). This method does
     * not check for request attribute {@value #REQUEST_VAADIN_STATIC_FILE_PATH}
     * 
     * @param request
     * @return
     */
    private String getWebApplicationsStaticFileLocation(
            HttpServletRequest request) {
        String staticFileLocation;
        // if property is defined in configurations, use that
        staticFileLocation = getApplicationOrSystemProperty(
                PARAMETER_VAADIN_RESOURCES, null);
        if (staticFileLocation != null) {
            return staticFileLocation;
        }

        // the last (but most common) option is to generate default location
        // from request

        // if context is specified add it to widgetsetUrl
        String ctxPath = request.getContextPath();

        // FIXME: ctxPath.length() == 0 condition is probably unnecessary and
        // might even be wrong.

        if (ctxPath.length() == 0
                && request.getAttribute("javax.servlet.include.context_path") != null) {
            // include request (e.g portlet), get context path from
            // attribute
            ctxPath = (String) request
                    .getAttribute("javax.servlet.include.context_path");
        }

        // Remove heading and trailing slashes from the context path
        ctxPath = removeHeadingOrTrailing(ctxPath, "/");

        if (ctxPath.equals("")) {
            return "";
        } else {
            return "/" + ctxPath;
        }
    }

    /**
     * Gets an application or system property value.
     * 
     * @param parameterName
     *            the Name or the parameter.
     * @param defaultValue
     *            the Default to be used.
     * @return String value or default if not found
     */
    private String getApplicationOrSystemProperty(String parameterName,
            String defaultValue) {

        String val = null;

        // Try application properties
        val = getApplicationProperty(parameterName);
        if (val != null) {
            return val;
        }

        // Try system properties
        val = getSystemProperty(parameterName);
        if (val != null) {
            return val;
        }

        return defaultValue;
    }

    /**
     * Remove any heading or trailing "what" from the "string".
     * 
     * @param string
     * @param what
     * @return
     */
    private static String removeHeadingOrTrailing(String string, String what) {
        while (string.startsWith(what)) {
            string = string.substring(1);
        }

        while (string.endsWith(what)) {
            string = string.substring(0, string.length() - 1);
        }

        return string;
    }
}
