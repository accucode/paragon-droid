/*
  Copyright (c) 2005-2012 Wyatt Love

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */

package com.kodemore.utility;

public class KmLoremIpsum
{
    public static String getSentence()
    {
        String p = getParagraph();
        int i = p.indexOf(".");
        return p.substring(0, i + 1);
    }

    public static String getParagraph()
    {
        return ""
            + "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy "
            + "nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi "
            + "enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis "
            + "nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in "
            + "hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat "
            + "nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit "
            + "praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Nam "
            + "liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id "
            + "quod mazim placerat facer possim assum. Typi non habent claritatem insitam; est "
            + "usus legentis in iis qui facit eorum claritatem. Investigationes demonstraverunt "
            + "lectores legere me lius quod ii legunt saepius. Claritas est etiam processus "
            + "dynamicus, qui sequitur mutationem consuetudium lectorum. Mirum est notare quam "
            + "littera gothica, quam nunc putamus parum claram, anteposuerit litterarum formas "
            + "humanitatis per seacula quarta decima et quinta decima. Eodem modo typi, qui nunc "
            + "nobis videntur parum clari, fiant sollemnes in futurum.";
    }
}
