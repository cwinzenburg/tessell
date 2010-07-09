package org.gwtmpv.widgets;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;

public interface IsStyle {

  String getOpacity();

  void setOpacity(double value);

  void setWidth(double value, Unit unit);

  void setHeight(double value, Unit unit);

  void setFontWeight(FontWeight value);

  void setColor(String value);

  void setBackgroundColor(String value);

  void setLeft(double value, Unit unit);

  void setTop(double value, Unit unit);

  void setMargin(double value, Unit unit);

  void setMarginTop(double value, Unit unit);

  void setMarginBottom(double value, Unit unit);

  void setMarginLeft(double value, Unit unit);

  void setMarginRight(double value, Unit unit);

  void setOverflow(Overflow value);

  void setBorderColor(String value);

  void setBorderStyle(BorderStyle value);

  void setBorderWidth(double value, Unit unit);

  String getDisplay();

  void setDisplay(Display value);

  String getProperty(String name);

  void setProperty(String name, String value);

  void setProperty(String name, double value, Unit unit);

  void setPropertyPx(String name, int value);

}