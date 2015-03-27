/*******************************************************************************
 * Copyright 2012 Steven Rudenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.spitspce.space;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.nirhart.parallaxscroll.views.ParallaxListView;

import java.util.ArrayList;
import java.util.List;

public class GASFragment extends Fragment {
    public static final String TAG = GASFragment.class.getSimpleName();

    private static final String ABOUT_SCHEME = "category";
    private static final String ABOUT_AUTHORITY = "gas";
    public static final Uri ABOUT_URI = new Uri.Builder()
            .scheme(ABOUT_SCHEME)
            .authority(ABOUT_AUTHORITY)
            .build();

    private int[] indexes = new int[MainTileActivity.EventsCollection.size()];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.category_layout, container, false);

        ParallaxListView listView = (ParallaxListView)v.findViewById(R.id.listView);
        ArrayList<EventItem> eventList = new ArrayList<>();
        int k=0;
        for(int i=0;i<MainTileActivity.EventsCollection.size();i++) {
            if(MainTileActivity.EventsCollection.get(i).getEventCategory().equals("Games And Sports")) {
                eventList.add(MainTileActivity.EventsCollection.get(i));
                indexes[k]=MainTileActivity.EventsCollection.get(i).getEventUID();
                ++k;
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), EventDetailActivity.class);
                i.putExtra("event_value", indexes[position]);
                getActivity().startActivity(i);
            }
        });
        
        listView.setAdapter(new EventItemAdapter(getActivity(), R.layout.event_item, eventList));

        return v;
    }
}
