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
package com.kodemore.test;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.kodemore.collection.KmList;
import com.kodemore.collection.KmOrderedMap;
import com.kodemore.utility.KmRandom;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmOneLineView;
import com.kodemore.view.KmRowLayout;

/**
 * Test the use of the ExpandableListView.
 * 
 * This is only partially working, but demonstrates basic functionality.
 * Note that the tree really wants to use long ids to track selected nodes.
 * 
 * Also, the view does something goofy when you set the adapter such that
 * getAdapter does not actually return the adapter that you set.  This makes
 * it very awkward to track and notify observers when the data is changed.
 * 
 * Additional work would be needed to test alternative approaches and to 
 * wrap this in a simplified interface before using it in the application.
 */
public class TyTreeActivity
    extends KmActivity
{
    //##################################################
    //# static
    //##################################################

    private static KmOrderedMap<String,Group> _groups;

    //##################################################
    //# variables
    //##################################################

    private ExpandableListView                _treeView;

    //##################################################
    //# create
    //##################################################

    @Override
    protected void init()
    {
        generateGroups();

        _treeView = newTreeView();
        _treeView.setAdapter(new Adapter());
    }

    private ExpandableListView newTreeView()
    {
        return new ExpandableListView(getContext())
        {
            // none
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = new KmColumnLayout(ui());
        root.addViewFullWeight(_treeView);

        KmRowLayout row;
        row = root.addEvenRow();
        row.addButton("Generate", newGenerateAction());
        row.addButton("Test", newTestAction());
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newGenerateAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleGenerate();
            }
        };
    }

    private KmAction newTestAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleTest();
            }
        };
    }

    //##################################################
    //# lifecycle
    //##################################################

    @Override
    protected void onResumeAsync()
    {
        super.onResumeAsync();
    }

    //##################################################
    //# handle
    //##################################################

    private void handleGenerate()
    {
        generateGroups();

        /*
         * Class Cast Exception
         * 
         * Despite the fact that we previously called _treeView.setAdapter(...)
         * the following code fails with a class cast exception.  The tree view's
         * setAdapter method is likely creating a wrapper, but this makes it 
         * impossible for us to call the adapter's methods (like notifyDataChanged)
         * unless we separately bind an additional variable for our adapter.
         */
        // 
        //        Adapter a;
        //        a = (Adapter)_treeView.getAdapter();
        //        a.notifyDataChanged();
    }

    private void handleTest()
    {
        // none
    }

    //##################################################
    //# utility
    //##################################################

    private void generateGroups()
    {
        _groups = new KmOrderedMap<String,Group>();

        KmList<TyAnimal> all = TyAnimal.tools.getRandomAnimals();
        for ( TyAnimal e : all )
        {
            String color = e.getColor();

            Group group = _groups.get(color);
            if ( group == null )
            {
                group = new Group();
                group.name = color;
                _groups.put(color, group);
            }

            Child child;
            child = new Child();
            child.name = e.getType();

            group.children.add(child);
        }
    }

    //##################################################
    //# custom adapter
    //##################################################

    private class Adapter
        implements ExpandableListAdapter
    {
        //==================================================
        //= tree :: observers
        //==================================================

        private KmList<DataSetObserver> _observers = new KmList<DataSetObserver>();

        //==================================================
        //= tree :: general
        //==================================================

        @Override
        public void registerDataSetObserver(DataSetObserver e)
        {
            _observers.add(e);
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver e)
        {
            _observers.remove(e);
        }

        /**
         * See comment above. 
         */
        @SuppressWarnings("unused")
        public void notifyDataChanged()
        {
            KmList<DataSetObserver> v = _observers;
            for ( DataSetObserver e : v )
                e.onChanged();
        }

        @Override
        public void onGroupExpanded(int groupPosition)
        {
            // none
        }

        @Override
        public void onGroupCollapsed(int groupPosition)
        {
            // none
        }

        @Override
        public boolean isEmpty()
        {
            return _groups.isEmpty();
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

        //==================================================
        //= tree :: groups
        //==================================================

        @Override
        public long getGroupId(int i)
        {
            return _groups.getValueAt(i).id;
        }

        @Override
        public int getGroupCount()
        {
            return _groups.size();
        }

        @Override
        public Object getGroup(int i)
        {
            return _groups.getValueAt(i);
        }

        @Override
        public long getCombinedGroupId(long groupId)
        {
            return groupId;
        }

        @Override
        public View getGroupView(
            int groupPosition,
            boolean isExpanded,
            View oldView,
            ViewGroup parent)
        {
            Group group = _groups.getValueAt(groupPosition);

            KmOneLineView view;
            view = oldView instanceof KmOneLineView
                ? (KmOneLineView)oldView
                : ui().newOneLineView();
            view.setTextColor(Color.BLUE);
            view.setBackgroundColor(Color.YELLOW);
            view.setPadding(50, 0, 0, 0);
            view.setTextBold(group.name);
            return view;
        }

        //==================================================
        //= tree :: children
        //==================================================

        @Override
        public long getCombinedChildId(long groupId, long childId)
        {
            return childId;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            return _groups.getValueAt(groupPosition).children.size();
        }

        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            Group group = _groups.getValueAt(groupPosition);
            Child child = group.children.get(childPosition);
            return child.id;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            Group group = _groups.getValueAt(groupPosition);
            Child child = group.children.get(childPosition);
            return child;
        }

        @Override
        public View getChildView(
            int groupPosition,
            int childPosition,
            boolean isLastChild,
            View oldView,
            ViewGroup parent)
        {
            Group group = _groups.getValueAt(groupPosition);
            Child child = group.children.get(childPosition);

            KmOneLineView view;
            view = oldView instanceof KmOneLineView
                ? (KmOneLineView)oldView
                : ui().newOneLineView();
            view.setPadding(100, 0, 0, 0);
            view.setTextBold(child.name);
            return view;
        }
    }

    //##################################################
    //# domain classes
    //##################################################

    private class Group
    {
        long          id       = KmRandom.getLong();
        String        name;
        KmList<Child> children = new KmList<Child>();
    }

    private class Child
    {
        long   id = KmRandom.getLong();
        String name;
    }
}
