# -- Project information -----------------------------------------------------

project = 'AL5 Java Examples'
author = 'ITER Organization with Poznan Supercomputing and Networking Center (PSNC)'


extensions = [
    "sphinx.ext.todo",
    "sphinx.ext.autosectionlabel",
    "sphinx.ext.autodoc",
    "sphinx.ext.intersphinx",
    "sphinx.ext.mathjax",
    "sphinx.ext.napoleon",
    "sphinx_immaterial",
    "sphinx_immaterial.apidoc.python.apigen",
    'sphinx_tabs.tabs'
]

intersphinx_mapping = {
    'python': ('https://docs.python.org/3', None),
    'numpy': ('https://numpy.org/doc/stable/', None),
}

html_theme = "sphinx_immaterial"
html_theme_options = {
    "repo_url": "https://git.iter.org/projects/IMAS/repos/access-layer",
    "repo_name": "Access Layer",
    "icon": {
        "repo": "fontawesome/brands/bitbucket",
    },
    "features": [
        "navigation.sections",
        "navigation.instant",
        "navigation.top",
        "toc.follow",
        "toc.sticky",
        "announce.dismiss",
        "navigation.path"
    ],
    "palette": [
        {
            "media": "(prefers-color-scheme: light)",
            "scheme": "default",
            "primary": "indigo",
            "accent": "green",
            "toggle": {
                "icon": "material/lightbulb-outline",
                "name": "Switch to dark mode",
            },
        },
        {
            "media": "(prefers-color-scheme: dark)",
            "scheme": "slate",
            "primary": "light-blue",
            "accent": "lime",
            "toggle": {
                "icon": "material/lightbulb",
                "name": "Switch to light mode",
            },
        },
    ],
    "version_dropdown": True,
    "version_json": "../versions.js",
}

object_description_options = [
    (".*", dict(include_fields_in_toc=False)),
    (".*parameter", dict(include_in_toc=False)),
]

sphinx_immaterial_generate_extra_admonitions = True
sphinx_immaterial_custom_admonitions = [
    {
        "name": "output",
        "color": (247, 98, 245),
        "icon": "fontawesome/solid/terminal",
    },

]
exclude_patterns = ["_build", "al-venv"]

# Directly set the pygments_style instead of adding it
pygments_style = 'sphinx'
