package net.sistemasparainter.foxtrot.daragadito.foxtrot;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriasFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public CategoriasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_produtos, container, false);
        viewPager = (ViewPager) fragmentView.findViewById(R.id.pager);
        tabLayout = (TabLayout) fragmentView.findViewById(R.id.tabsCategorias);
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return fragmentView;
    }

}
