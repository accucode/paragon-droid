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

/**
 * A convenient way to create a runnable that loops forever
 * until cancelled.  See also, KmRunnable.
 */
public abstract class KmLoopRunnable
    extends KmRunnable
{
    //##################################################
    //# variables
    //##################################################

    private boolean _cancelled;

    //##################################################
    //# accessing
    //##################################################

    public void cancel()
    {
        _cancelled = true;
    }

    public boolean isCancelled()
    {
        return _cancelled;
    }

    public boolean isNotCancelled()
    {
        return !isCancelled();
    }

    //##################################################
    //# run
    //##################################################

    @Override
    public final void run()
    {
        onStart();

        while ( !isCancelled() )
            runOnce();

        onStop();
    }

    protected abstract void runOnce();

    //##################################################
    //# events
    //##################################################

    /**
     * Called once before the main loop.
     */
    protected void onStart()
    {
        // subclass override
    }

    /**
     * Called once after the loop terminates (typically due 
     * to the client having called cancel).
     */
    protected void onStop()
    {
        // subclass override
    }
}
